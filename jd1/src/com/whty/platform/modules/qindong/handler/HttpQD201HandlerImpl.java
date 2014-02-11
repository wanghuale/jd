package com.whty.platform.modules.qindong.handler;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whty.platform.common.config.Global;
import com.whty.platform.common.utils.HttpURL;
import com.whty.platform.modules.bussiness.entity.Provider;
import com.whty.platform.modules.bussiness.entity.ServiceRecord;
import com.whty.platform.modules.bussiness.entity.Services;
import com.whty.platform.modules.bussiness.service.ServiceRecordService;
import com.whty.platform.modules.qindong.utils.HttpQDUtils;
import com.whty.platform.modules.sp.entity.HttpBusinessRequest;
import com.whty.platform.modules.sp.entity.HttpBusinessResponse;
import com.whty.platform.modules.sp.handler.HttpBusinessHandler;

/**
 * 擎动手机商品查询和充值
 * 
 * @author qimin
 * 
 */
@Service("httpQD201Handler")
public class HttpQD201HandlerImpl implements HttpBusinessHandler {
	private static Logger logger = LoggerFactory.getLogger(HttpQD201HandlerImpl.class);

	@Autowired
	private ServiceRecordService serviceRecordService;

	public HttpBusinessResponse handler(HttpBusinessRequest httpBusinessRequest) throws Exception {
		Services services = httpBusinessRequest.getServices();
		// 取得URL
		StringBuffer interFaceUrl = new StringBuffer("http://");
		Provider provider = services.getProvider();
		interFaceUrl.append(provider.getIp());
		interFaceUrl.append(":").append(provider.getPort());
		interFaceUrl.append(services.getUri());
		logger.debug(interFaceUrl.toString());
		String replyMessage = "";

		// 请求
		HttpURL http = new HttpURL();

		Double price = getTradeMoney(httpBusinessRequest.getMessage(), services.getId(),
				httpBusinessRequest.getRemoteIp(), httpBusinessRequest.getAppId());

		// 获取key
		Map<String, Object> tempvalue = HttpQDUtils.jsonToMap(httpBusinessRequest.getMessage());
		@SuppressWarnings("unchecked")
		Map<String, Object> actionInfoMap = (Map<String, Object>) tempvalue.get("ACTION_INVOKER");
		String os = actionInfoMap.get("OSNAME") + "";
		String mallKey = "";
		if ("ios".equals(os.toLowerCase())) {
			mallKey = Global.getConfig("qingdong.mallkey.ios");
		} else {
			mallKey = Global.getConfig("qingdong.mallKey.Android");
		}

		logger.debug("加密前：" + httpBusinessRequest.getMessage());
		// 加密
		String value = HttpQDUtils.decodeToString(httpBusinessRequest.getMessage(), mallKey);

		logger.debug("加密后：" + value);

		// 调用擎动接口
		replyMessage = http.doPost(interFaceUrl.toString(), value.getBytes());
		logger.debug("返回结果：" + replyMessage);
		HttpBusinessResponse httpBusinessResponse = new HttpBusinessResponse();

		// 解密
		replyMessage = HttpQDUtils.encodeJsonString(replyMessage, mallKey);

		logger.debug("返回结果解密：" + replyMessage);

		httpBusinessResponse.setReplyMessage(replyMessage);
		httpBusinessResponse.setPrice(price);

		Map<String, Object> valueMap = HttpQDUtils.jsonToMap(replyMessage);
		if ("000000".equals(valueMap.get("ACTION_RETURN_CODE") + "")) {
			httpBusinessResponse.setStatus("1");
		} else {
			httpBusinessResponse.setStatus("2");
		}
		// 返回数据
		return httpBusinessResponse;
	}

	private double getTradeMoney(String json, Long sid, String ip, String appid) {
		if (appid.equals("202") || appid.equals("302")) {
			return this.getTradeMoney202(json, sid, ip, appid);
		} else if (appid.equals("402")) {
			return this.getTradeMoney402(json, sid, ip, appid);
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	private double getTradeMoney202(String json, Long sid, String ip, String appid) {
		try {
			// 获取查询的产品列表
			ServiceRecord s = serviceRecordService.findServiceRecordByIp(sid, ip);
			String message = s.getDoData();
			Map<String, Object> valueMap = HttpQDUtils.jsonToMap(message);
			Map<String, Object> actionInfoMap = (Map<String, Object>) valueMap.get("ACTION_INFO");
			List<Map<String, Object>> products = (List<Map<String, Object>>) actionInfoMap.get("PRODUCTS");

			// 获取下订单的产品ID
			Map<String, Object> valueMap2 = HttpQDUtils.jsonToMap(json);
			Map<String, Object> actionInfoMap2 = (Map<String, Object>) valueMap2.get("ACTION_INFO");
			String productid = (String) actionInfoMap2.get("PRODUCT_ID");

			// 在列表中找出改产品
			for (Map<String, Object> map : products) {
				String product_id = map.get("PRODUCT_ID") + "";
				String purchase_price = map.get("PURCHASE_PRICE") + "";
				if (productid.equals(product_id)) {
					return NumberUtils.toDouble(purchase_price, 0);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	private double getTradeMoney402(String json, Long sid, String ip, String appid) {
		try {
			// 获取下订单的产品ID
			Map<String, Object> valueMap2 = HttpQDUtils.jsonToMap(json);
			Map<String, Object> actionInfoMap2 = (Map<String, Object>) valueMap2.get("ACTION_INFO");
			// 在列表中找出改产品
			String purchase_price = actionInfoMap2.get("AMOUNT") + "";
			return NumberUtils.toDouble(purchase_price, 0);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 0;
	}
}