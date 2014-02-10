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
import com.whty.platform.modules.hongcheng.service.Clear804Service;
import com.whty.platform.modules.sp.entity.HttpBusinessRequest;
import com.whty.platform.modules.sp.entity.HttpBusinessResponse;
import com.whty.platform.modules.sp.entity.SpConsts;
import com.whty.platform.modules.sp.handler.HttpBusinessHandler;
import com.whty.platform.modules.sp.utils.DecodeUtils;
import com.whty.platform.modules.sp.utils.OtherUtils;

/**
 * 洪城一卡通清算中心联机交易报文：联机消费及代缴交易
 * @author jincheng
 * @date 2013-11-29
 */
@Service("HttpHongCheng804Handler")
public class HttpHongCheng804HandlerImpl implements HttpBusinessHandler {

	private static Logger logger = LoggerFactory.getLogger(HttpHongCheng804HandlerImpl.class);
	
	@Autowired
	private Clear804Service clear804Service;
	
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
		String jsonParam = DecodeUtils.enData(hbr.getActionInfo(), key);
		logger.debug("请求参数解密:"+jsonParam);
		
		/********报文体内容:messageBodySts*******/
		Map<String, Object> paramMap = JsonMapper.getInstance().fromJson(jsonParam, Map.class);//将接受的json字符串参数转成map结构
		String[] messageBodySts = new String[]{
				OtherUtils.checkString((String) paramMap.get("Citycode"), ""),
				"0000000000000000",
				"666666666666",
				"666666666666",
				"00000000000000000000000000000000",
				"00000000000000000000","00000"
				};
		HashMap<String, Object> hashMap = this.clear804Service.sendIbspIIPosMessage804(messageBodySts);
		String jsonStr = JsonMapper.getInstance().toJson(hashMap);
		
		HttpBusinessResponse httpBusinessResponse = new HttpBusinessResponse();
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
