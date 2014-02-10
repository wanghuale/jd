package com.whty.platform.modules.sp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OtherUtils {
	private static Logger logger = LoggerFactory.getLogger(OtherUtils.class);

	/**
	 * 校验参数是否为null、空字符串，否则就用默认值
	 * @param str 待校验的参数
	 * @param def 默认值
	 * @return
	 */
	public static String checkString(String str, String def) {
		if (str == null || "".equals(str) || "null".equals(str)) {
			return def;
		} else {
			return str.replace("-", "").replace(" ", "");
		}
	}

	/**
	 * 将字符串数组转成字符串，打印用
	 * @param s
	 * @return
	 */
	public static String strToString(String[] s) {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		sb.append("{");
		while (i < s.length) {
			sb.append(s[i]);
			i++;
			if (i < s.length) {
				sb.append(",");
			}
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 在LOG中打印MAP的参数
	 * @param map
	 */
	public static void readParamMap(HashMap<String, Object> map) {
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String next = it.next();
			if ("list".equals(next)) {
				ArrayList<HashMap<String, Object>> ma = (ArrayList<HashMap<String, Object>>) map.get(next);
				for (int i = 0; i < ma.size(); i++) {
					readParamMap(ma.get(i));
					System.out.println("************************第" + i + "条记录************************");
				}
			} else {
				logger.info(next + ":" + (String) map.get(next));
			}

		}
	}

	/**
	 * 格式化日期
	 * @param date
	 * @return
	 */
	public static String formatDate(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat newSDF = new SimpleDateFormat("yyyy-MM-dd");
		if (date == null || "".equals(date) || "00000000".equals(date)) {
			date = "";
		} else if (date.length() == 8) {
			try {
				Date d = sdf.parse(date);
				date = newSDF.format(d);
			} catch (ParseException e) {

			}
		}
		return date;
	}
	
	/**
	 * 格式化时间
	 * @param date
	 * @return
	 */
	public static String formatTime(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat newSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (date == null || "".equals(date) || "00000000000000".equals(date)) {
			date = "";
		} else if (date.length() == 14) {
			try {
				Date d = sdf.parse(date);
				date = newSDF.format(d);
			} catch (ParseException e) {

			}
		}
		return date;
	}
}
