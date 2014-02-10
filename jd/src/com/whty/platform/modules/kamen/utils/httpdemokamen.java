package com.whty.platform.modules.kamen.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.whty.platform.common.utils.HttpURL;

public class httpdemokamen {

	public static void main(String[] args) {
		httpdemokamen.all(args);
	}

	public static void all(String[] args) {
		// 下单接口：http://api.kamennet.com/API/Order/Charge.aspx
		// 下单接口(话费)：http://api.kamennet.com/API/Order/ChargePhone.aspx
		// 查询接口：http://api.kamennet.com/API/Order/Query_New.aspx
		// 查询商品类目：http://api.kamennet.com/api/GoodsInterface/GoodsCatalog.aspx
		// 查询商品信息：http://api.kamennet.com/api/GoodsInterface/GoodsInfo.aspx

		System.getProperties().setProperty("proxySet", "true"); // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
		System.getProperties().setProperty("http.proxyHost", "10.8.15.118");
		System.getProperties().setProperty("http.proxyPort", "606");
		String interFaceUrl = "http://api.kamennet.com/api/GoodsInterface/GoodsCatalog.aspx";
		HttpURL http = new HttpURL();
		String json = "";
		try {
			URL s = httpdemokamen.class.getClassLoader().getResource("json.txt");
			System.out.println(s.getPath());
			json = FileUtils.readFileToString(new File(s.getPath()), "UTF-8");
			System.out.println(json);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String restr = null;
		try {
			Map<String, String> map = new HashMap<String, String>();
			try {
				System.out.println(java.net.URLDecoder.decode("%e6%b9%96%e5%8c%97%e7%a7%bb%e5%8a%a8", "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			map.put("CustomerID", "801479");
			map.put("Sign", "FCD4CEE20CA4FDDAAC0023FB860DDCA2");
			restr = http.doPost(interFaceUrl, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(restr);
	}
}
