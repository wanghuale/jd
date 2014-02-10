package com.whty.platform.modules.hongcheng.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whty.platform.common.utils.mapper.JsonMapper;
import com.whty.platform.modules.bussiness.entity.Provider;
import com.whty.platform.modules.bussiness.entity.Services;
import com.whty.platform.modules.hongcheng.service.Clear801Service;
import com.whty.platform.modules.sp.entity.HttpBusinessRequest;
import com.whty.platform.modules.sp.entity.HttpBusinessResponse;
import com.whty.platform.modules.sp.entity.SpConsts;
import com.whty.platform.modules.sp.handler.HttpBusinessHandler;
import com.whty.platform.modules.sp.utils.DecodeUtils;

/**
 * 洪城一卡通清算中心联机交易报文：主密钥下载交易
 * @author jincheng
 * @date 2013-11-29
 */
@Service("HttpHongCheng801Handler")
public class HttpHongCheng801HandlerImpl implements HttpBusinessHandler {
	
	private static Logger logger = LoggerFactory.getLogger(HttpHongCheng801HandlerImpl.class);
	
	@Autowired
	private Clear801Service clear801Service;
	
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
		
		//报文体内容
		String[] messageBodySts = new String[]{"15000000","0000000000000000","666666666666","666666666666","00000000000000000000000000000000","00000000000000000000","00000"};
		HashMap<String, Object> hashMap = this.clear801Service.sendIbspIIPosMessage801(messageBodySts);
		String jsonStr = JsonMapper.getInstance().toJson(hashMap);
		
		httpBusinessResponse.setActionName(hbr.getActionName());
		// actioninfo 数据填充
		Map<String, Object> actinoMap = new HashMap<String, Object>();
		actinoMap.put("PARAM", "param");
		actinoMap.put("PHONE_PROVINCE", "暂无");
		actinoMap.put("MERCHANT_TYPE", "暂无");
		actinoMap.put("MERCHANT_ID", "暂无");
		httpBusinessResponse.addActionValue("ACTION_RETURN_CODE", SpConsts.ACTION_RETURN_CODE_000000);
		httpBusinessResponse.addActionValue("ACTION_INFO",DecodeUtils.deData(jsonStr, key));
//		httpBusinessResponse.setStatus("1");
		
		return httpBusinessResponse;
	}

}
