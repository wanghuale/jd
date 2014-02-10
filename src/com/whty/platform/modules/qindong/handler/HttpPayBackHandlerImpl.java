package com.whty.platform.modules.qindong.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whty.platform.common.utils.HttpURL;
import com.whty.platform.modules.bussiness.entity.OrderRecord;
import com.whty.platform.modules.bussiness.service.OrderRecordService;

@Service("httpQDPayBackHandler")
public class HttpPayBackHandlerImpl implements HttpPayBackHandler {

	private static Logger logger = LoggerFactory.getLogger(HttpPayBackHandlerImpl.class);

	@Autowired
	private OrderRecordService orderRecordService;

	public HttpPayBackResponse handler(HttpPayBackRequest request) throws Exception {
		OrderRecord orderRecord = orderRecordService.getByOrderID(request.getBody().getOrderId());
		logger.debug(request.getDatejson());
		if (orderRecord == null) {
			HttpPayBackResponse response = new HttpPayBackResponse();
			response.getHead().setMethod(request.getHead().getMethod());
			response.getHead().setSerialNumber(request.getHead().getSerialNumber());
			response.getHead().setVersion(request.getHead().getVersion());
			response.getBody().setCode("000000");
			response.getBody().setMessage("success");
		}
		orderRecord.setRemarks(request.getDatejson());
		// 请求
		HttpURL http = new HttpURL();
		String replyMessage = http.doPost(orderRecord.getCallBackUrl(), request.getDatejson().getBytes());
		logger.debug(replyMessage);
		HttpPayBackResponse response = new HttpPayBackResponse();
		response.initByJson(replyMessage);
		response.getHead().setMethod(request.getHead().getMethod());
		response.getHead().setSerialNumber(request.getHead().getSerialNumber());
		response.getHead().setVersion(request.getHead().getVersion());
		response.getBody().setCode("000000");
		response.getBody().setMessage("success");
		orderRecordService.save(orderRecord);
		return response;
	}
}