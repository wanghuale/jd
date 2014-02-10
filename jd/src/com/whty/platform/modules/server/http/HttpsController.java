package com.whty.platform.modules.server.http;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
import com.whty.platform.modules.bussiness.service.ServiceRecordService;
import com.whty.platform.modules.bussiness.service.ServicesService;
import com.whty.platform.modules.qindong.handler.HttpPayBackHandler;
import com.whty.platform.modules.qindong.handler.HttpPayBackRequest;
import com.whty.platform.modules.qindong.handler.HttpPayBackResponse;
import com.whty.platform.modules.sp.entity.HttpBusinessRequest;
import com.whty.platform.modules.sp.entity.HttpBusinessResponse;
import com.whty.platform.modules.sp.handler.HttpBusinessHandler;
import com.whty.platform.modules.sys.utils.DictUtils;

/**
 * 
 * @author qimin
 * @fun http 协议访问
 */
@Controller
@RequestMapping(value = "/https")
public class HttpsController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(HttpsController.class);

	@Autowired
	private ServicesService servicesService;

	@Autowired
	private ServiceRecordService serviceRecordService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public void index(HttpServletRequest request, HttpServletResponse response) {
		System.out.println(this);
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
			// 记录日志
			serviceRecord.setUsername(ip);
			serviceRecord.setReData(json);
			serviceRecord.setDelFlag("0");
			serviceRecord.setIp(ip);
			serviceRecord.setCreateDate(new Timestamp(new Date().getTime()));
			serviceRecord.setTradeMoney(0D);

			// 解析参数
			HttpBusinessRequest hbr = null;
			try {
				hbr = new HttpBusinessRequest(json);
			} catch (Exception e) {
				backMessage = "{\"ACTION_RETURN_CODE\":\"100044\",\"MESSAGE\":\"Json Is Error!\"}";
				throw e;
			}
			hbr.setRemoteIp(ip);
			serviceRecord.setReService(hbr.getActionName());

			// 验证读取服务
			List<Services> list = servicesService.findServicesByIp(hbr.getAppId(), ip);
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
						} else {
							backMessage = "{\"ACTION_RETURN_CODE\":\"100045\",\"MESSAGE\":\"服务handler设置出错，请联系管理员设置handler!\"}";
							serviceRecord.setStatus("2");
						}
					} else {
						backMessage = "{\"ACTION_RETURN_CODE\":\"100046\",\"MESSAGE\":\"协议暂时不支持!\"}";
						serviceRecord.setStatus("2");
					}
				}
			} else {
				backMessage = "{\"ACTION_RETURN_CODE\":\"100047\",\"MESSAGE\":\"服务未开通，拒绝访问,请联系管理员开通服务！\"}";
				serviceRecord.setStatus("2");
			}
			logger.debug(backMessage);
			serviceRecord.setDoData(backMessage);
		} catch (Exception e) {
			logger.error(e.getMessage());
			backMessage = "{\"ACTION_RETURN_CODE\":\"100000\",\"MESSAGE\":\"系统繁忙，请稍后！\"}";
			serviceRecord.setStatus("2");
		} finally {
			RenderUtils.renderJson(response, backMessage);
			serviceRecord.setRemarks(remarks);
			serviceRecordService.save(serviceRecord);
		}
	}

	@RequestMapping(value = "payback", method = RequestMethod.POST)
	public void postMoney(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("设置代理payback");
		// 设置代理
		String backMessage = "";
		HttpPayBackRequest rqt = new HttpPayBackRequest();
		try {
			String json = IOUtils.toString(request.getInputStream(), "UTF-8");
			HttpPayBackHandler handler = SpringContextHolder.getBean("httpQDPayBackHandler");// 获取对应服务处理器
			rqt.initByJson(json);
			HttpPayBackResponse rpe = handler.handler(rqt);// 发送请求
			backMessage = rpe.tojson();
		} catch (Exception e) {
			logger.error(e.getMessage());
			HttpPayBackResponse rpe = new HttpPayBackResponse();
			backMessage = rpe.tojson("012101", "120319181305000011", "1", "000001", "error!");
		} finally {
			RenderUtils.renderJson(response, backMessage);
		}
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public void get(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("设置代理http");
		RenderUtils.renderJson(response, "{\"ACTION_RETURN_CODE\":\"100000\",\"MESSAGE\":\"Only POST Allowed\"}");
	}

	@RequestMapping(value = "payback", method = RequestMethod.GET)
	public void getMoney(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("设置代理GET");
		RenderUtils.renderJson(response, "{\"ACTION_RETURN_CODE\":\"100000\",\"MESSAGE\":\"Only POST Allowed\"}");
	}

	@RequestMapping(value = "test")
	public void tse(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("银联 callback");
		try {
			String json = IOUtils.toString(request.getInputStream(), "UTF-8");
			System.out.println(json);
		} catch (Exception e) {
		} finally {
		}
		RenderUtils.renderJson(response, "{\"ACTION_RETURN_CODE\":\"100000\",\"MESSAGE\":\"Only POST Allowed test\"}");
	}

}
