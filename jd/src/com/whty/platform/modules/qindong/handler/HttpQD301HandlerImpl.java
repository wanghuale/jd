package com.whty.platform.modules.qindong.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.whty.platform.modules.sp.entity.HttpBusinessRequest;
import com.whty.platform.modules.sp.entity.HttpBusinessResponse;
import com.whty.platform.modules.sp.handler.HttpBusinessHandler;


@Service("httpQD301Handler")
public class HttpQD301HandlerImpl implements HttpBusinessHandler {
	private static Logger logger = LoggerFactory.getLogger(HttpQD301HandlerImpl.class);

	public HttpBusinessResponse handler(HttpBusinessRequest httpBusinessRequest) {
		String replyMessage = "QD301";
		logger.debug(replyMessage);
		HttpBusinessResponse httpBusinessResponse = new HttpBusinessResponse();
		return httpBusinessResponse;
	}
}