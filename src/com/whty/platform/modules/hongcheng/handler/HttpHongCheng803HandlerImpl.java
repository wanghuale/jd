package com.whty.platform.modules.hongcheng.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.whty.platform.modules.bussiness.entity.Provider;
import com.whty.platform.modules.bussiness.entity.Services;
import com.whty.platform.modules.sp.entity.HttpBusinessRequest;
import com.whty.platform.modules.sp.entity.HttpBusinessResponse;
import com.whty.platform.modules.sp.handler.HttpBusinessHandler;
import com.whty.platform.modules.sp.utils.DecodeUtils;

/**
 * 洪城一卡通清算中心联机交易报文：联机卡操作
 * @author jincheng
 * @date 2013-11-29
 */
@Service("HttpHongCheng803Handler")
public class HttpHongCheng803HandlerImpl implements HttpBusinessHandler {

	private static Logger logger = LoggerFactory.getLogger(HttpHongCheng803HandlerImpl.class);
	
	@Override
	public HttpBusinessResponse handler(HttpBusinessRequest hbr) throws Exception {
		Services services = hbr.getServices();
		String key = hbr.getKey();
		// 取得URL
		StringBuffer interFaceUrl = new StringBuffer("http://");
		Provider provider = services.getProvider();
		interFaceUrl.append(provider.getIp());
		interFaceUrl.append(":").append(provider.getPort());
		interFaceUrl.append(services.getUri());
		logger.debug(interFaceUrl.toString());
		
		//接受请求参数进行解密
		String enDataStr = DecodeUtils.enData(hbr.getActionInfo(), key);
		logger.debug("请求参数解密:"+enDataStr);
		
		HttpBusinessResponse httpBusinessResponse = new HttpBusinessResponse();
		
		return httpBusinessResponse;
	}
}
