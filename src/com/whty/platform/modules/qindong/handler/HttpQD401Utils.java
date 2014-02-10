package com.whty.platform.modules.qindong.handler;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.platform.common.utils.mapper.JsonMapper;
import com.whty.platform.common.utils.security.SecurityUtil;

public class HttpQD401Utils {

	private static Logger logger = LoggerFactory.getLogger(HttpQD401Utils.class);

	/**
	 * 解密json
	 */
	public static String encodeJsonString(String json, String mallKey) {
		try {
			return JsonMapper.getInstance().toJson(encodeJson(json, mallKey));
		} catch (Exception e) {
			return json;
		}
	}

	/**
	 * 解密json
	 */
	public static Map<String, Object> encodeJson(String json, String mallKey) throws Exception {
		Map<String, Object> contentMap = JsonMapper.getInstance().fromJson(json,
				JsonMapper.getInstance().createCollectionType(Map.class, String.class, Object.class));
		Map<String, Object> re = encodeActionInfo((String) contentMap.get("ACTION_INFO"), mallKey);
		contentMap.put("ACTION_INFO", re);
		return contentMap;
	}

	/**
	 * 解密actionInfo
	 */
	public static String encodeActionInfoS(String actionInfo, String mallKey) throws Exception {
		if (StringUtils.isBlank(actionInfo) || actionInfo.trim().length() < 32) {
			logger.error("actionInfo 为空或长度过短");
			return null;
		}
		// 主 非空判断
		if (mallKey == null) {
			logger.error("密钥为空");
			return null;
		}
		// 获取随机数
		String randData = actionInfo.substring(0, 32);
		// 获取应用密文
		String singData = actionInfo.substring(32, actionInfo.length());
		// 获取过程密钥
		String processKey = SecurityUtil.desecb(mallKey, randData, 0);
		// 解密singData
		String actionInfoString = SecurityUtil.desecb(processKey, singData, 1);
		// 将16进制数字解码成字符串,适用于所有字符（包括中文）
		actionInfoString = SecurityUtil.hexStringToString(actionInfoString);
		// 最后一个'80'出现的位置
		int num = actionInfoString.lastIndexOf("80");
		// 截取actionInfoString
		if (num != -1) {
			actionInfoString = actionInfoString.substring(0, num);
		}
		// actionInfoString转换为字符串
		actionInfoString = new String(SecurityUtil.hexToBytes(actionInfoString), "UTF-8");
		return actionInfoString;
	}

	/**
	 * 解密actionInfo
	 */
	public static Map<String, Object> encodeActionInfo(String actionInfo, String mallKey) throws Exception {
		if (StringUtils.isBlank(actionInfo) || actionInfo.trim().length() < 32) {
			logger.error("actionInfo 为空或长度过短");
			return null;
		}
		// 主 非空判断
		if (mallKey == null) {
			logger.error("密钥为空");
			return null;
		}
		// 获取随机数
		String randData = actionInfo.substring(0, 32);
		// 获取应用密文
		String singData = actionInfo.substring(32, actionInfo.length());
		// 获取过程密钥
		String processKey = SecurityUtil.desecb(mallKey, randData, 0);
		// 解密singData
		String actionInfoString = SecurityUtil.desecb(processKey, singData, 1);
		// 将16进制数字解码成字符串,适用于所有字符（包括中文）
		actionInfoString = SecurityUtil.hexStringToString(actionInfoString);
		// 最后一个'80'出现的位置
		int num = actionInfoString.lastIndexOf("80");
		// 截取actionInfoString
		if (num != -1) {
			actionInfoString = actionInfoString.substring(0, num);
		}
		// actionInfoString转换为字符串
		actionInfoString = new String(SecurityUtil.hexToBytes(actionInfoString), "UTF-8");
		// 生成actionInfoMap
		Map<String, Object> actionInfoMap = JsonMapper.getInstance().fromJson(actionInfoString,
				JsonMapper.getInstance().createCollectionType(Map.class, String.class, Object.class));
		if (actionInfoMap == null) {
			actionInfoMap = new HashMap<String, Object>();
			List<Object> list = JsonMapper.getInstance().fromJson(actionInfoString,
					JsonMapper.getInstance().createCollectionType(List.class, Object.class));

			actionInfoMap.put("list", list);
		}
		return actionInfoMap;
	}

	/**
	 * 加密decodeToString
	 */
	public static String decodeToString(String value, String mallKey) throws UnsupportedEncodingException {
		Map<String, Object> returnMap = JsonMapper.getInstance().fromJson(value,
				JsonMapper.getInstance().createCollectionType(Map.class, String.class, Object.class));
		return decodeMapToString(returnMap, mallKey);
	}

	/**
	 * 加密decodeMapToString
	 */
	public static String decodeMapToString(Map<String, Object> returnMap, String mallKey)
			throws UnsupportedEncodingException {
		return JsonMapper.getInstance().toJson(decodeMap(returnMap, mallKey));
	}

	/**
	 * 加密returnMap
	 */
	public static Map<String, Object> decodeMap(Map<String, Object> returnMap, String mallKey)
			throws UnsupportedEncodingException {
		// 主 非空判断
		if (mallKey == null) {
			logger.error("密钥为空");
			return null;
		}
		// 返回Map非空判断
		if (returnMap == null) {
			logger.error("返回数据为空");
			return null;
		}
		// 需要加密的字符串
		String actionInfo = "";
		// 如果action_info为空，则不需要加密
		if (returnMap.get("ACTION_INFO") == null) {
			return returnMap;
		}
		// 航班的action_info是list集合 （特殊处理）
		else if (returnMap.get("ACTION_INFO") instanceof List) {
			actionInfo = JsonMapper.getInstance().toJson(returnMap.get("ACTION_INFO"));
		} else if (returnMap.get("ACTION_INFO") instanceof String) {
			actionInfo = returnMap.get("ACTION_INFO").toString();
		}
		// 其他action_info是map
		else {
			@SuppressWarnings("unchecked")
			Map<String, Object> actionInfoMap = (Map<String, Object>) returnMap.get("ACTION_INFO");
			actionInfo = JsonMapper.getInstance().toJson(actionInfoMap);
		}
		// 生成随机数
		String randData = SecurityUtil.generalStringToAscii(8) + SecurityUtil.generalStringToAscii(8);
		// 获取过程密钥
		String processKey = SecurityUtil.desecb(mallKey, randData, 0);
		// 将actionInfo转换16进制后，补80
		actionInfo = SecurityUtil.padding80(SecurityUtil.bytesToHexString(actionInfo.getBytes("UTF-8")));
		// 将字符串编码成16进制数字,适用于所有字符（包括中文）
		actionInfo = SecurityUtil.encodeHexString(actionInfo);
		// 加密
		actionInfo = SecurityUtil.desecb(processKey, actionInfo, 0);
		// 最终生成密文
		String end = randData + actionInfo;
		returnMap.put("ACTION_INFO", end);
		return returnMap;
	}

	public static Map<String, Object> jsonToMap(String json) throws UnsupportedEncodingException {
		return JsonMapper.getInstance().fromJson(json,
				JsonMapper.getInstance().createCollectionType(Map.class, String.class, Object.class));

	}
}
