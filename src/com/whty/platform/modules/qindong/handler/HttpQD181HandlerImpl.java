package com.whty.platform.modules.qindong.handler;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whty.platform.common.utils.HttpURL;
import com.whty.platform.modules.bussiness.entity.OrderRecord;
import com.whty.platform.modules.bussiness.entity.Provider;
import com.whty.platform.modules.bussiness.entity.Services;
import com.whty.platform.modules.bussiness.service.OrderRecordService;
import com.whty.platform.modules.qindong.utils.HttpQDUtils;
import com.whty.platform.modules.sp.entity.HttpBusinessRequest;
import com.whty.platform.modules.sp.entity.HttpBusinessResponse;
import com.whty.platform.modules.sp.handler.HttpBusinessHandler;

@Service("httpQD181Handler")
public class HttpQD181HandlerImpl implements HttpBusinessHandler {
	private static Logger logger = LoggerFactory.getLogger(HttpQD181HandlerImpl.class);

	@Autowired
	private OrderRecordService orderRecordService;

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

		Map<String, Object> ss = HttpQDUtils.jsonToMap(httpBusinessRequest.getMessage());
		@SuppressWarnings("unchecked")
		Map<String, Object> actionInfoMap = (Map<String, Object>) ss.get("ACTION_INFO");
		String call_back_url = actionInfoMap.get("CALLBACK_URL") + "";
		String order_id = actionInfoMap.get("ORDER_ID") + "";
		Double amount = NumberUtils.toDouble(actionInfoMap.get("AMOUNT") + "");
		OrderRecord orderRecord = new OrderRecord();
		orderRecord.setConsumer(httpBusinessRequest.getConsumer());
		orderRecord.setCallBackUrl(call_back_url);
		orderRecord.setOrderId(order_id);
		orderRecord.setCreateDate(new Date());
		orderRecord.setAmount(amount);
		// {\"ACTION_RETURN_CODE\":\"000000\",\"SP_ID\":\"0074\",\"ORDER_ID\":\"12384091\",\"MERCHANT_NAME\":\"821420145110032\",\"MERCHANT_ID\":\"821420145110032\",\"TERMINAL_ID\":\"82142014\"}

		// 请求
		HttpURL http = new HttpURL();
		replyMessage = http.doPost(interFaceUrl.toString(), httpBusinessRequest.getMessage().getBytes());
		logger.debug(replyMessage);
		HttpBusinessResponse httpBusinessResponse = new HttpBusinessResponse();
		httpBusinessResponse.setReplyMessage(replyMessage);
		// 保存返回数据
		this.saveResponse(httpBusinessResponse);
		// 返回数据
		httpBusinessResponse.setReplyMessage(replyMessage);
		httpBusinessResponse.setPrice(0D);
		Map<String, Object> valueMap = HttpQDUtils.jsonToMap(replyMessage);
		if ("000000".equals(valueMap.get("ACTION_RETURN_CODE") + "")) {
			httpBusinessResponse.setStatus("1");
			String pay_order_id = valueMap.get("ORDER_ID") + "";
			orderRecord.setPayOrderId(pay_order_id);
			orderRecordService.save(orderRecord);
		} else {
			httpBusinessResponse.setStatus("2");
		}
		return httpBusinessResponse;
	}

	private void saveResponse(HttpBusinessResponse httpBusinessResponse) throws Exception {

	}
}