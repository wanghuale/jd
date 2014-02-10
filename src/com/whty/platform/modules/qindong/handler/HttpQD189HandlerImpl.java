package com.whty.platform.modules.qindong.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.whty.platform.modules.sp.entity.HttpBusinessRequest;
import com.whty.platform.modules.sp.entity.HttpBusinessResponse;
import com.whty.platform.modules.sp.handler.HttpBusinessHandler;


@Service("httpQD189Handler")
public class HttpQD189HandlerImpl implements HttpBusinessHandler {
	private static Logger logger = LoggerFactory.getLogger(HttpQD189HandlerImpl.class);

	public HttpBusinessResponse handler(HttpBusinessRequest httpBusinessRequest) {
		String replyMessage = "{\"ACTION_RETURN_CODE\":\"000000\"}";
		logger.debug(replyMessage);
		HttpBusinessResponse httpBusinessResponse = new HttpBusinessResponse();
		httpBusinessResponse.setReplyMessage(replyMessage);
		httpBusinessResponse.setReplyMessage(replyMessage);
		httpBusinessResponse.setPrice(0D);
		return httpBusinessResponse;
	}
}