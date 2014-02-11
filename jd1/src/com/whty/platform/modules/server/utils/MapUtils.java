package com.whty.platform.modules.server.utils;

import java.util.Map;

public class MapUtils {

	public static String getValue(Map<String, Object> paramMap, String key) {
		if (paramMap == null) {
			return "";
		}
		Object o = paramMap.get(key);
		return o == null ? "" : (String) o;
	}

}
