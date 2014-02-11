package com.whty.platform.modules.hongcheng.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.whty.platform.common.service.BaseService;

/**
 * POS联机卡混合操作Service
 * @author 金成
 * @version 2013-11-29
 */
@Component
@Transactional(readOnly = true)
public class Clear802Service extends BaseService {

	private static Logger logger = LoggerFactory.getLogger(Clear802Service.class);
	
	// 发送报文头格式
	public static final String[] PARAM_HEAD = new String[] { "2-N", "4-N", "14-N", "8-N", "2-N" };
	// 发送报文体格式:主密钥下载交易
	public static final String[] PARAM_BODY_IBSPII_KEYDOWNLOAD = new String[] { "8-N", "16-N", "12-N", "12-N", "32-A",
			"20-A", "5-N" };
	// 定义返回报文内容:主密钥下载
	public static final String[] PARAM_ATTRIBUTE_IBSPII_KEYDOWNLOAD = new String[] { "unitId", "samId", "posId",
			"termId", "keySet", "reserved", "responseCode" };

	/**
	 * 处理报文内容: POS联机卡混合操作
	 * 
	 * @param messageBodySts报文体内容
	 * @return 返回被解析的报文信息
	 * @throws Exception
	 */
	public static HashMap<String, Object> sendIbspIIPosMessage802(String[] messageBodySts) throws Exception {
		String sendMsg = null;
		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
		String messageDate = fmt.format(new Date());

		String[] messageTopSts = new String[] { "01", "5000", "20131012150923", "00000000", "00" };// 报文头内容:通讯押码不知道则填写00000000
		String[] paramBodySts = null;// 报文体内容格式
		String[] paramNameSts = null; // 返回Map KEY值
		String[] paramList = null; // 通常为查询多条数据用于解析用的。
		String[] keyList = null;

		paramBodySts = PARAM_BODY_IBSPII_KEYDOWNLOAD;
		paramNameSts = PARAM_ATTRIBUTE_IBSPII_KEYDOWNLOAD;

		/**** 此处写个返回的假数据 ***/
		hashMap.put("msg", "{'messageType':'0800'," + "'cardTime':'120327'," + "'cardDate':'1126',"
				+ "'retrievalNo':''," + "'responseCode':''," + "'ticNo':'30313233'," + "'cardNo':'01',"
				+ "'orderType':'00'," + "'batchNo':'123456'," + "'networkNo':'380'," + "'keyInfo':''}");

		return hashMap;
	}
}
