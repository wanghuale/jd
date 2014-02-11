package com.whty.platform.modules.luojia.utils;


public class StringUtils {
	public static String getValue(String value) throws Exception{
		if(value == null || value.trim().equals("")) return "";
		return value;
	}
	
	public static String getUTF8Value(String value) throws Exception{
		if(value == null || value.trim().equals("")) return "";
		return java.net.URLEncoder.encode(value, "UTF-8");
	}
}
