package com.whty.platform.modules.server.http;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whty.platform.common.base.web.BaseController;
import com.whty.platform.common.utils.PropertiesLoader;
import com.whty.platform.common.utils.RenderUtils;
import com.whty.platform.common.utils.SpringContextHolder;
import com.whty.platform.modules.bussiness.entity.Provider;
import com.whty.platform.modules.bussiness.entity.ServiceRecord;
import com.whty.platform.modules.bussiness.entity.Services;
import com.whty.platform.modules.bussiness.service.ConsumerService;
import com.whty.platform.modules.bussiness.service.ServiceRecordService;
import com.whty.platform.modules.bussiness.service.ServicesService;
import com.whty.platform.modules.sp.entity.HttpBusinessRequest;
import com.whty.platform.modules.sp.entity.HttpBusinessResponse;
import com.whty.platform.modules.sp.entity.SpConsts;
import com.whty.platform.modules.sp.handler.HttpBusinessHandler;
import com.whty.platform.modules.sys.utils.DictUtils;

/**
 * 
 * @author qimin
 * @fun http 协议访问
 */
@Controller
@RequestMapping(value = "/app")
public class SpAppController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(SpAppController.class);

	@Autowired
	private ServicesService servicesService;

	@Autowired
	private ServiceRecordService serviceRecordService;

	@Autowired
	private ConsumerService consumerService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public void index(HttpServletRequest request, HttpServletResponse response) {
		// 设置代理
		String proxySet = PropertiesLoader.getPValue("proxySet");
		if ("true".equals(proxySet)) {
			String proxyHost = PropertiesLoader.getPValue("http.proxyHost");
			String proxyPort = PropertiesLoader.getPValue("http.proxyPort");
			System.getProperties().setProperty("proxySet", proxySet); // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
			System.getProperties().setProperty("http.proxyHost", proxyHost);
			System.getProperties().setProperty("http.proxyPort", proxyPort);
		}

		ServiceRecord serviceRecord = new ServiceRecord();
		String remarks = "", backMessage = "";
		try {
			// 取服务
			String ip = RenderUtils.getIpAddress(request);
			String json = IOUtils.toString(request.getInputStream(), "UTF-8");
			logger.debug("请求数据：" + json);
			serviceRecord.setIp(ip);
			serviceRecord.setReData(json);
			serviceRecord.setDelFlag(ServiceRecord.DEL_FLAG_NORMAL);
			serviceRecord.setCreateDate(new Timestamp(new Date().getTime()));
			serviceRecord.setTradeMoney(0D);

			// 解析参数
			HttpBusinessRequest hbr = null;
			try {
				hbr = new HttpBusinessRequest(json);
				hbr.setRemoteIp(ip);
				hbr.setConsumer(consumerService.findByUsername(hbr.getActionUser()));
				if (hbr.getConsumer() == null || hbr.getConsumer().getId() == null) {
					backMessage = addReturnJosn("100005");
					return;
				} else if (hbr.getConsumer().getIp().indexOf(ip) < 0) {
					backMessage = addReturnJosn("100006");
					return;
				}
			} catch (Exception e) {
				backMessage = addReturnJosn("100044");
				throw e;
			}
			// 回调URL
			String notifyurl = PropertiesLoader.getPValue("notifyurl");
			if (StringUtils.isBlank(notifyurl)) {
				notifyurl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath() + "/notify";
			}
			hbr.setNotifyurl(notifyurl);
			logger.debug(hbr.getNotifyurl());

			// 记录日志
			serviceRecord.setUsername(hbr.getActionUser());
			serviceRecord.setReService(hbr.getActionName());

			// 验证读取服务
			List<Services> list = servicesService.findServicesByUsername(hbr.getAppId(), hbr.getActionUser());

			if (list.size() == 1) {// 获取用户设置的服务
				Services services = list.get(0);
				logger.debug(services.getName());
				serviceRecord.setDoService(services);
				if (services != null) {
					Provider provider = services.getProvider();
					String protocol = DictUtils.getDictLabel(provider.getProtocol(), "sys_protocol", "");
					if ("HTTP".equals(protocol)) {
						HttpBusinessHandler handler = SpringContextHolder.getBean(services.getBusinessHandler());// 获取对应服务处理器
						if (handler != null) {
							hbr.setServices(services);
							HttpBusinessResponse httpBusinessResponse = (HttpBusinessResponse) handler.handler(hbr);// 发送请求
							backMessage = httpBusinessResponse.getReplyMessage();
							serviceRecord.setStatus(httpBusinessResponse.getStatus());
							serviceRecord.setTradeMoney(httpBusinessResponse.getPrice());
							serviceRecord.setRemarks(hbr.getNotifyurl());
						} else {
							backMessage = addReturnJosn("100045");
							serviceRecord.setStatus("2");
						}
					} else {
						backMessage = addReturnJosn("100046");
						serviceRecord.setStatus("2");
					}
				}
			} else {
				backMessage = addReturnJosn("100047");
				serviceRecord.setStatus("2");
			}
			logger.debug(backMessage);
			serviceRecord.setDoData(backMessage);
		} catch (Exception e) {
			logger.error(e.getMessage());
			backMessage = addReturnJosn("100003");
			serviceRecord.setStatus("2");
		} finally {
			RenderUtils.renderJson(response, backMessage);
			serviceRecord.setRemarks(remarks);
			serviceRecordService.save(serviceRecord);
		}
	}

	public String addReturnJosn(String code, String message) {
		return "{\"ACTION_RETURN_CODE\":\"" + code + "\",\"MESSAGE\":\"" + message + "\"}";
	}

	public String addReturnJosn(String code) {
		return "{\"ACTION_RETURN_CODE\":\"" + code + "\",\"MESSAGE\":\"" + SpConsts.getErrorValue(code) + "\"}";
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public void err(HttpServletResponse response) {
		String backMessage = "{\"ACTION_RETURN_CODE\":\"" + 10000 + "\",\"MESSAGE\":\"请使用POST请求! \"}";
		RenderUtils.renderJson(response, backMessage);
	}

}
