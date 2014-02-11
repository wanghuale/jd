package com.whty.platform.modules.server.http;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whty.platform.common.base.web.BaseController;
import com.whty.platform.common.utils.DateUtils;
import com.whty.platform.common.utils.RenderUtils;
import com.whty.platform.common.utils.security.MD5;
import com.whty.platform.modules.bussiness.entity.OrderRecord;
import com.whty.platform.modules.bussiness.service.OrderRecordService;
import com.whty.platform.modules.kamen.entity.KamenUser;
import com.whty.platform.modules.sp.service.SpService;

/**
 * 
 * @author qimin
 * @fun http 协议访问
 */
@Controller
@RequestMapping(value = "/notify")
public class SpCallBackController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(SpCallBackController.class);

	@Autowired
	private OrderRecordService orderRecordService;

	@Autowired
	private SpService spService;

	@RequestMapping(value = "")
	public void index(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("收到卡门回调请求：" + RenderUtils.getIpAddress(request));
		try {
			String chargetime = request.getParameter("ChargeTime");
			String customerorderno = request.getParameter("CustomerOrderNo");
			String orderno = request.getParameter("OrderNo");
			String remark = request.getParameter("ReMark");
			String status = request.getParameter("Status");
			String sign = request.getParameter("Sign");
			logger.debug("卡门回调请求处理中");
			Enumeration<String> enume = request.getParameterNames();
			while (enume.hasMoreElements()) {
				String key = enume.nextElement();
				logger.debug("请求参数：" + key + "=" + request.getParameter(key));
			}
			String sData = "chargetime=" + chargetime + "&customerorderno=" + customerorderno + "&orderno=" + orderno + "&remark=" + remark
					+ "&status=" + status + new KamenUser().getKey();
			MD5 md5 = new MD5();
			String ssign = md5.getMD5Byte(sData.getBytes("utf-8")).toLowerCase();

			logger.debug("获取参数签名：sign = " + ssign);
			logger.debug("检查参数：" + (ssign.equals(sign) ? "检查通过" : "参数可能被篡改"));
			if (!ssign.equals(sign)) {
				RenderUtils.renderJson(response, "False");
				return;
			}
			OrderRecord orderRecord = orderRecordService.get(NumberUtils.toLong(customerorderno));
			if ("True".equals(status)) {
				orderRecord.setStatus("1");
			} else {
				orderRecord.setStatus("2");
			}
			orderRecord.setRemarks(remark);
			orderRecord.setUpdateDate(DateUtils.parseDate(chargetime));
			orderRecordService.save(orderRecord);
			System.out.println(orderRecord.getPayOrderId());
			RenderUtils.renderJson(response, "True");
			spService.callback(orderRecord);
			logger.debug("卡门回调请求处理完毕");
			spService.call();
			return;
		} catch (Exception e) {
			RenderUtils.renderJson(response, "False");
			return;
		}

	}

	@RequestMapping(value = "call", method = RequestMethod.POST)
	public void call(HttpServletRequest request, HttpServletResponse response) {
		String json = "数据异常";
		try {
			json = IOUtils.toString(request.getInputStream(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("请求数据：" + json);
	}

}
