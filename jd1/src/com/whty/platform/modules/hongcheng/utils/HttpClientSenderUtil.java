package com.whty.platform.modules.hongcheng.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HttpClientSenderUtil {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 组装请求XML报文 (门户使用)
	 * 
	 * @param interfaceId
	 *            接口ID,服务端根据接口ID进行对应的业务处理
	 * @param param
	 *            HashMap 请求参数,Key:参数名 Value:参数值
	 * @return
	 */
	public static String createRequestXml(String interfaceId,
			HashMap<String, Object> param) {
		Document doc = DocumentFactory.getInstance().createDocument();

		Element root = doc.addElement("request");
		Element interfacez = root.addElement("interfaceId");
		interfacez.setText(interfaceId);

		Iterator keySetIt = param.keySet().iterator();
		while (keySetIt.hasNext()) {
			String key = (String) keySetIt.next();
			Object value = param.get(key);
			String valueStr = (value == null ? "" : value.toString());

			Element el = root.addElement(key);
			el.setText(valueStr);
		}

		return doc.asXML();
	}

	/**
	 * 解析XML报文 当响应报文中包含列表记录时,返回的HashMap中 列表记录Key:list Value:ArrayList对象
	 * ,ArrayList中每个元素为一条记录的HashMap(该HashMap中的Key:单条列表记录 参数名,Value:单条列表记录 参数值)
	 * 
	 * @param xml
	 *            响应XML
	 * @return HashMap 解析出的响应报文 Key:响应参数名 Value: 响应参数值 ,
	 *         默认拥有一个Key=errorCode的参数,Value=-2时表示响应报文格式不为xml
	 */
	public static HashMap<String, Object> parseRequestAndResponseXml(String xml) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			List elementList = root.elements();
			for (int i = 0; i < elementList.size(); i++) {
				Element el = (Element) elementList.get(i);
				if (el.getName().equalsIgnoreCase("list")) {
					HashMap<String, String> listMap = new HashMap<String, String>();
					List elChilds = el.elements();
					for (int j = 0; j < elChilds.size(); j++) {
						Element childElement = (Element) elChilds.get(j);
						listMap.put(childElement.getName(), childElement.getText());
					}
					listData.add(listMap);
				} else {
					resultMap.put(el.getName(), el.getText());
				}
			}
			if (listData.size() > 0) {
				resultMap.put("list", listData);
			}
		} catch (DocumentException e) {
			// -2 格式错误
			e.printStackTrace();
			resultMap.put("errorCode", "-2");
		}
		return resultMap;
	}

	/**
	 * 组装响应报文 (自定义报文 使用)
	 * 
	 * @param headerParam
	 *            responseData[0]的ArrayList的每个索引值对应的参数名称
	 * @param listDataParam
	 *            单个列表记录的ArrayList的每个索引值对应的参数名称
	 * @param responseData
	 *            responseData[0] 列表记录之外的所有信息 responseData[1..n]
	 *            第1-第N个ArrayList未列表记录
	 * @return
	 */
	public static String createResponseXml(String[] headerParam,
			String[] listDataParam, List<ArrayList<String>> responseData) {
		ArrayList headerList = responseData.get(0);
		Document doc = DocumentFactory.getInstance().createDocument();
		Element root = doc.addElement("response");

		for (int i = 0; i < headerParam.length; i++) {
			Element el = root.addElement(headerParam[i]);
			el.setText(headerList.get(i).toString());
		}

		if (listDataParam != null) {
			for (int i = 1; i < responseData.size(); i++) {
				ArrayList dataList = responseData.get(i);
				Element listEl = root.addElement("list");
				for (int j = 0; j < listDataParam.length; j++) {
					Element dataEl = listEl.addElement(listDataParam[j]);
					dataEl.setText(dataList.get(j).toString());
				}
			}
		}

		return doc.asXML();
	}

	/**
	 * 以HashMap 组装响应XML报文 (8583 使用)
	 * 
	 * @param param
	 *            HashMap 请求参数,Key:参数名 Value:参数值
	 * @return
	 */
	public static String createResponseXml(Map<String, Object> param) {
		Document doc = DocumentFactory.getInstance().createDocument();

		Element root = doc.addElement("response");

		Iterator<String> keySetIt = param.keySet().iterator();
		while (keySetIt.hasNext()) {
			String key = (String) keySetIt.next();
			if (key.equals("list")) {
				Object value = param.get(key);
				List<HashMap<String, String>> list = (List<HashMap<String, String>>) value;
				if (list != null) {
					for (HashMap<String, String> hashMap2 : list) {
						Element el = root.addElement(key);
						Iterator<String> keySetIt2 = hashMap2.keySet().iterator();
						while (keySetIt2.hasNext()) {
							String key2 = (String) keySetIt2.next();
							Element el2 = el.addElement(key2);
							Object value2 = hashMap2.get(key2);
							String valueStr2 = (value2 == null ? "" : value2.toString());
							el2.setText(valueStr2);
						}
					}
				}
			} else {
				Object value = param.get(key);
				String valueStr = (value == null ? "" : value.toString());
				Element el = root.addElement(key);
				el.setText(valueStr);
			}
		}

		return doc.asXML();
	}

	/**
	 * moved to HttpClientSenderUtilTest
	 * 
	 * public static void main(String[] args){ String hxesb ="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"
	 * ; sendPosToBank(hxesb); // testCreateResponseXML(); }
	 */

	public static void testCreateResponseXML() {
		List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList headerList = new ArrayList();
		headerList.add("0000");
		headerList.add("10");
		headerList.add("15");
		list.add(headerList);

		for (int i = 0; i < 3; i++) {
			ArrayList dataList = new ArrayList();
			dataList.add(5.00 + i);
			dataList.add("10000" + i);
			dataList.add("张" + i);
			list.add(dataList);
		}

		new HttpClientSenderUtil();
		String xml = HttpClientSenderUtil.createResponseXml(new String[] {
				"errorCode", "rows", "count" }, new String[] { "money",
				"cardNo", "name" }, list);
		System.out.println(xml);
	}

	public static void testParseResponseXml() {
		StringBuffer xmlBuffer = new StringBuffer("<response>");
		xmlBuffer.append("<errorCode>0000</errorCode>");
		xmlBuffer.append("<rows>5</rows>");
		xmlBuffer.append("<count>15</count>");
		for (int i = 0; i < 5; i++) {
			xmlBuffer.append("<list>");
			xmlBuffer.append("<money>5.00</money>");
			xmlBuffer.append("<cardNo>10000" + i + "</cardNo>");
			xmlBuffer.append("<name>張" + i + "</name>");
			xmlBuffer.append("</list>");
		}
		xmlBuffer.append("</response>");
		new HttpClientSenderUtil();
		Map<String, Object> resultMap = HttpClientSenderUtil.parseRequestAndResponseXml(xmlBuffer.toString());
		System.out.println("errorCode = " + resultMap.get("errorCode"));
		System.out.println("rows = " + resultMap.get("rows"));
		System.out.println("count = " + resultMap.get("count"));
		List dataList = (List) resultMap.get("list");
		for (int i = 0; i < dataList.size(); i++) {
			HashMap map = (HashMap) dataList.get(i);
			System.out.print("money = " + map.get("money") + "     ");
			System.out.print("cardNo = " + map.get("cardNo") + "     ");
			System.out.println("name = " + map.get("name"));
			System.out
					.println("**************************************************");
		}
	}

	public static String createResponseXml2(HashMap param) {
		Document doc = DocumentFactory.getInstance().createDocument();
		Element root = doc.addElement("response");
		Iterator keySetIt = param.keySet().iterator();
		while (keySetIt.hasNext()) {
			String key = (String) keySetIt.next();
			if (key.equals("list")) {
				Object value = param.get(key);
				Element el = root.addElement(key);
				List<HashMap<String, String>> list = (List<HashMap<String, String>>) value;
				if (list != null) {
					for (HashMap<String, String> hashMap2 : list) {
						Iterator keySetIt2 = hashMap2.keySet().iterator();
						while (keySetIt2.hasNext()) {
							String key2 = (String) keySetIt2.next();
							Element el2 = el.addElement(key2);
							Object value2 = hashMap2.get(key2);
							String valueStr2 = (value2 == null ? "" : value2
									.toString());
							el2.setText(valueStr2);
						}
					}
				}
			} else {
				Object value = param.get(key);
				String valueStr = (value == null ? "" : value.toString());
				Element el = root.addElement(key);
				el.setText(valueStr);
			}
		}
		return doc.asXML();
	}
}
