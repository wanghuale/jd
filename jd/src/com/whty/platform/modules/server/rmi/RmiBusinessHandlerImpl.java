package com.whty.platform.modules.server.rmi;


public class RmiBusinessHandlerImpl implements RmiBusinessHandler {

	public RmiBusinessResult executeBusiness(RmiBusinessRequest rmiBusinessRequest) {
		String replyMessage = null;
		String responseCode = "0000";

		RmiBusinessResult businessResult = new RmiBusinessResult();
		businessResult.setResponseCode(responseCode);
		businessResult.setReplyMessage(replyMessage);

		return businessResult;
	}
}