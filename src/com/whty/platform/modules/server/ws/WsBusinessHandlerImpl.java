package com.whty.platform.modules.server.ws;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(serviceName = "wsBusinessService", endpointInterface = "com.whty.platform.modules.server.ws.WsBusinessHandler", targetNamespace = "http://www.whty.com.cn")
public class WsBusinessHandlerImpl implements WsBusinessHandler {
	private static Logger logger = LoggerFactory.getLogger(WsBusinessHandlerImpl.class);

	public WsBusinessResult executeBusiness(WsBusinessRequest wsBusinessRequest) {
		String replyMessage = null;
		String responseCode = "0000";
		logger.info(responseCode);
		WsBusinessResult businessResult = new WsBusinessResult();
		businessResult.setResponseCode(responseCode);
		businessResult.setReplyMessage(replyMessage);

		return businessResult;
	}
}