package com.whty.platform.modules.qindong.handler;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BusinessHandler {
	private static Logger logger = LoggerFactory.getLogger(BusinessHandler.class);

	public BusinessResponse handler(BusinessRequest businessRequest) {
		BusinessResponse businessResponse = null;
		logger.info("");
		return businessResponse;
	}

	public BusinessResponse getBusinessResponse(String messageFormat, String message, String errorCode) {
		BusinessResponse response = new BusinessResponse();
		response.setMessageFormat(messageFormat);
		if (StringUtils.isBlank(message)) {
			message = errorCode;
		}
		response.setMessage(message);
		response.setResponseCode(errorCode);
		return response;
	}
}