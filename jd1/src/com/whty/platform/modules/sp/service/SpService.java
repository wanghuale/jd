/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.sp.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whty.platform.common.utils.DateUtils;
import com.whty.platform.common.utils.HttpURL;
import com.whty.platform.common.utils.mapper.JsonMapper;
import com.whty.platform.common.utils.security.MD5;
import com.whty.platform.modules.bussiness.entity.OrderRecord;
import com.whty.platform.modules.bussiness.service.OrderRecordService;
import com.whty.platform.modules.kamen.entity.KamenUser;
import com.whty.platform.modules.kamen.entity.OrderInfo;
import com.whty.platform.modules.kamen.service.KemenService;
import com.whty.platform.modules.sp.utils.DecodeUtils;

/**
 * 卡门接口
 * 
 * @author qimin
 * @version 2013-07-10
 */
@Component
public class SpService {

	private static Logger logger = LoggerFactory.getLogger(SpService.class);

	@Autowired
	private OrderRecordService orderRecordService;

	@Autowired
	private KemenService kemenService;

	public void callback(OrderRecord orderRecord) {
		String interFaceUrl = orderRecord.getConsumer().getNotifyurl();
		logger.debug("SP开始回调" + orderRecord.getConsumer().getName() + ":" + interFaceUrl);
		HttpURL http = new HttpURL();
		Map<String, Object> infomap = new HashMap<String, Object>();
		infomap.put("CUSTOMER_ORDER_NO", orderRecord.getPayOrderId());
		infomap.put("ORDER_RESULT", orderRecord.getStatus());
		infomap.put("ORDER_DES", orderRecord.getRemarks());
		String rejson = JsonMapper.getInstance().toJson(infomap);
		logger.debug("请求参数:" + rejson);
		Map<String, Object> remap = new HashMap<String, Object>();
		try {
			remap.put("ACTINO_INFO", DecodeUtils.deData(rejson, orderRecord.getConsumer().getkey()));
		} catch (Exception e) {
			e.printStackTrace();
			remap.put("ACTINO_INFO", "");
		}
		String json = JsonMapper.getInstance().toJson(remap);
		String restr = null;
		try {
			restr = http.doPost(interFaceUrl, json.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("请求完毕收到返回报文:" + ("True".equals(restr) || "true".equals(restr) ? "对方确认接收" : "对方未接收到请求"));
	}

	public void call() {
		String StartTime = DateUtils.getDate() + " 00:00:00";
		String EndTime = DateUtils.getDate() + " 23:59:59";
		logger.debug("开始同步数据:日期" + StartTime + "至" + EndTime);
		try {
			kemenService = new KemenService();
			List<OrderInfo> list = kemenService.getGetOrderCustomers(new KamenUser(), StartTime, EndTime);
			logger.debug("请求卡门获取订单数量:" + list.size() + "个");
			for (OrderInfo order : list) {
				save(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save(OrderInfo order) {
		try {
			logger.debug("开始同步订单到数据库:SP订单号【" + order.getCustomerOrderNo() + "】,卡门订单号【" + order.getOrderId() + "】处理【"
					+ order.getOrderStatus() + "】");
			OrderRecord o = orderRecordService.get(NumberUtils.toLong(order.getCustomerOrderNo()));
			if (order.getOrderStatus().equals("成功")) {
				o.setStatus("1");
			} else if (order.getOrderStatus().equals("失败")) {
				o.setStatus("2");
			}
			o.setUpdateDate(DateUtils.parseDate(order.getChargeTime()));
			System.out.println(order.getCustomerOrderNo());
			orderRecordService.save(o);
			callback(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.getProperties().setProperty("proxySet", "true"); // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
		System.getProperties().setProperty("http.proxyHost", "10.8.15.118");
		System.getProperties().setProperty("http.proxyPort", "606");

		MD5 md5 = new MD5();
		String dd = "chargetime=2013-11-26 09:53:48&customerorderno=64&orderno=168993907&remark=充值失败：你的帐号无法存入Q币，单日累计存款金额超过系统限额或者其他原因充值失败&status=FalseBAA20338FD3E36050B62AD16226EA96D";
		String ssign;
		try {
			ssign = md5.getMD5Byte(dd.getBytes("utf-8"));
			System.out.println(ssign.toLowerCase());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// String StartTime = DateUtils.getDate() + " 00:00:00";
		// String EndTime = DateUtils.getDate() + " 23:59:59";
		// try {
		// KemenService kemenService = new KemenService();
		// List<OrderInfo> list = kemenService.getGetOrderCustomers(new
		// KamenUser(), StartTime, EndTime);
		// for (OrderInfo order : list) {
		// System.out.println(order.getCustomerOrderNo());
		// System.out.println(order.getOrderStatus());
		// }
		// } catch (KamenException e) {
		// e.printStackTrace();
		// }
	}
}
