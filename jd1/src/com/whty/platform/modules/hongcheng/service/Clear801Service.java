package com.whty.platform.modules.hongcheng.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.whty.platform.common.config.Global;
import com.whty.platform.common.service.BaseService;
import com.whty.platform.modules.hongcheng.net.SimpleMessageTcpClientHelper;
import com.whty.platform.modules.hongcheng.utils.HttpClientSenderUtil;
import com.whty.platform.modules.hongcheng.utils.StringUtil;
import com.whty.platform.modules.hongcheng.utils.UnpackMessageUtil;

/**
 * 主密钥下载交易Service
 * @author 金成
 * @version 2013-11-29
 */
@Component
@Transactional(readOnly = true)
public class Clear801Service extends BaseService {

	private static Logger logger = LoggerFactory.getLogger(Clear801Service.class);
	
	//发送报文头格式
	public static final String[] PARAM_HEAD = new String[]{"2-N","4-N","14-N","8-N","2-N"};
	//发送报文体格式:主密钥下载交易
	public static final String[] PARAM_BODY_IBSPII_KEYDOWNLOAD = new String[]{"8-N","16-N","12-N","12-N","32-A","20-A","5-N"};
	//定义返回报文内容:主密钥下载
	public static final String[] PARAM_ATTRIBUTE_IBSPII_KEYDOWNLOAD = new String[]{"unitId","samId","posId","termId","keySet","reserved","responseCode"};
	
	/**
	 * 处理报文内容: 主密钥下载
	 * @param messageBodySts报文体内容
	 * @return 返回被解析的报文信息
	 * @throws Exception
	 */
	public static HashMap<String, Object> sendIbspIIPosMessage801(String[] messageBodySts) throws Exception {
		String sendMsg  = null;
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
		String messageDate = fmt.format(new Date());
		
		String[] messageTopSts = new String[]{"01","5000","20131012150923","00000000","00"};//报文头内容:通讯押码不知道则填写00000000
		String[] paramBodySts = null;//报文体内容格式
		String[] paramNameSts = null; //返回Map KEY值
		String[] paramList = null; //通常为查询多条数据用于解析用的。
		String[] keyList = null;
		
		paramBodySts = PARAM_BODY_IBSPII_KEYDOWNLOAD;
		paramNameSts = PARAM_ATTRIBUTE_IBSPII_KEYDOWNLOAD;
		
		//转换为16进制报文头
		sendMsg = UnpackMessageUtil.packClearCenter(PARAM_HEAD, messageTopSts);//参数1：报文头格式，参数2：报文头内容
		//转换为16进制报文体
		String sendMsgBody = UnpackMessageUtil.packClearCenter(paramBodySts, messageBodySts);//参数1：报文体格式，参数2：报文体内容
//		messageTopSts[3] = UnpackMessageUtil.getMacII(messageDate, sendMsgBody);//计算通讯押码
		
		sendMsg += sendMsgBody;
		logger.debug(sendMsg);//拼装好的16进制的报文数据
		
		//发送报文给清算中心
		String returnCode = sendClearCenterMessage(sendMsg);
		logger.debug(returnCode);//清算中心返回结果
		
		/*
		 * 以下为清算中心返回结果的处理(返回的仍是16进制的数据)
		 */
		List<ArrayList<String>> bodyList = UnpackMessageUtil.unpackClearCenter(PARAM_HEAD, paramBodySts, paramList, returnCode);
		if(bodyList!=null){
			String errorCode = "";
			System.out.println(bodyList.get(0).get(4));
			errorCode = bodyList.get(0).get(4);
			
			hashMap.put("errorCode",errorCode);
			if(errorCode.equals("00")){
				hashMap.put("msg", "成功");
				//解析报文返回数据
				String returnXmlText = HttpClientSenderUtil.createResponseXml(paramNameSts, keyList, bodyList);
				//封装报文数据
				hashMap = HttpClientSenderUtil.parseRequestAndResponseXml(returnXmlText);
			}else{
				//TODO查询配置文件的错误信息
				hashMap.put("msg", Global.getConfig("city_msg."+errorCode));
			}
		}else{
			hashMap.put("errorCode", "-1");
			hashMap.put("msg", "报文通讯出错");
		}
		
		return hashMap;
	}
	
	/**
	 * 发送报文信息给清算中心
	 * @param sendMsg
	 * @return
	 * @throws Exception
	 */
	public static String sendClearCenterMessage(String sendMsg) throws Exception{
		logger.debug("发送报文"+sendMsg);
		//通过配置文件获取清算中心IP地址以及端口
		String clearCenter_message_ip = Global.getConfig("clearCenter_message_ip");
		int clearCenter_message_port = Integer.parseInt(Global.getConfig("clearCenter_message_port"));
		
		SimpleMessageTcpClientHelper simpleMessageTcpClientHelper = new SimpleMessageTcpClientHelper();
		
		String returnCode = simpleMessageTcpClientHelper.sendAsciiMessage(clearCenter_message_ip, clearCenter_message_port, sendMsg,"GBK");
		
		logger.debug("接收报文"+StringUtil.hexToAscii(returnCode));
		
		return returnCode;
	}
}
