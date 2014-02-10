package com.whty.platform.modules.qindong.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.whty.platform.common.utils.HttpURL;
import com.whty.platform.modules.bussiness.entity.Provider;
import com.whty.platform.modules.bussiness.entity.Services;
import com.whty.platform.modules.sp.entity.HttpBusinessRequest;
import com.whty.platform.modules.sp.entity.HttpBusinessResponse;
import com.whty.platform.modules.sp.handler.HttpBusinessHandler;

@Service("httpBusinessHandler")
public class HttpBusinessHandlerImpl implements HttpBusinessHandler {
	private static Logger logger = LoggerFactory.getLogger(HttpBusinessHandlerImpl.class);

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
		replyMessage = http.doPost(interFaceUrl.toString(), httpBusinessRequest.getMessage().getBytes());
		logger.debug(replyMessage);
		HttpBusinessResponse httpBusinessResponse = new HttpBusinessResponse();
		httpBusinessResponse.setReplyMessage(replyMessage);
		// 保存返回数据
		this.saveResponse(httpBusinessResponse);
		// 返回数据
		return httpBusinessResponse;
	}

	private void saveResponse(HttpBusinessResponse httpBusinessResponse) throws Exception {

	}
}