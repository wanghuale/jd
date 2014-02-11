package com.whty.platform.modules.luojia.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.whty.platform.common.utils.HttpURL;
import com.whty.platform.common.utils.XMLAdapter;
import com.whty.platform.common.utils.XMLAttributeAdapter;
import com.whty.platform.common.utils.XMLFileUtil;
import com.whty.platform.common.utils.mapper.JsonMapper;
/**
 * 珞迦航空
 * 
 * @author liugeng
 * 
 */
@Component
public class LuoJiaServiceImpl implements ILuoJiaService{
	private static Logger logger = LoggerFactory.getLogger(LuoJiaServiceImpl.class);
	public Map avh(String url, Map m) throws Exception {
		HttpURL httpURL = new HttpURL(); 
		String response = httpURL.doPost(url, m, false);
		logger.debug("avh response:" + response);	
		Map avhResponse = XMLFileUtil.getAttributeData(response, HashMap.class, new XMLAttributeAdapter<Map>(){
			private Map flightAttrbute;
			public void rowdata(String key,String value, Map attrbute, Map o) {
				if("DataList".equals(key)){
					o.put("DATALIST", attrbute);
				}else if ("Flight".equals(key)) {
					List flights = (List)((Map)o.get("DATALIST")).get("FLIGHTS");
					if(flights == null){
						flights = new ArrayList();
						((Map)o.get("DATALIST")).put("FLIGHTS", flights);
					}
					flightAttrbute = attrbute;
					flights.add(flightAttrbute);
				} else if ("Cabin".equals(key)) {
					List cabins = (List)flightAttrbute.get("CABINS");
					if(cabins == null){
						cabins = new ArrayList();
						flightAttrbute.put("CABINS", cabins);
					}
					cabins.add(attrbute);
				} else if ("Error".equals(key)) {
					o.put("ERROR", value);
				}		
			}
		});
		if(avhResponse.containsKey("ERROR")){
			avhResponse.put("RETURN_CODE", "0001");
		}else{
			avhResponse.put("ERROR", "");
			avhResponse.put("RETURN_CODE", "0000");
		}

		return avhResponse;
	}
	public Map createOrder(String url, Map m) throws Exception {
		HttpURL httpURL = new HttpURL(); 
		String response = httpURL.doPost(url, m, false);
		logger.info("createOrder response:" + response);
		Map avhResponse = XMLFileUtil.getDate(response, HashMap.class, new XMLAdapter<Map>(){
			private Map flightAttrbute;
			public void rowdata(String key,String value, Map o) {
				if("Status".equals(key)){
					o.put("STATUS", value);
				}else if ("Msg".equals(key)) {
					o.put("MSG", value);
				} else if ("OrderNo".equals(key)) {
					o.put("ORDERNO", value);
				} else if ("PnrNo".equals(key)) {
					o.put("PNRNO", value);
				}		
			}
		});
		return avhResponse;
	}
	
	public Map searchOrder(String url, Map m) throws Exception {
		HttpURL httpURL = new HttpURL(); 
		String response = httpURL.doPost(url, m, false);
		logger.info("searchOrder response:" + response);
		Map avhResponse = XMLFileUtil.getDate(response, HashMap.class, new XMLAdapter<Map>(){
			private Map flightAttrbute;
			public void rowdata(String key,String value, Map o) {
				if("Status".equals(key)){
					o.put("STATUS", value);
				}else if ("Msg".equals(key)) {
					o.put("MSG", value);
				}	
			}
		});
		return avhResponse;
	}
	
	public Map checkOrder(String url, Map m) throws Exception {
		HttpURL httpURL = new HttpURL(); 
		String response = httpURL.doPost(url, m, false);
		logger.info("checkOrder response:" + response);
		Map avhResponse = XMLFileUtil.getDate(response, HashMap.class, new XMLAdapter<Map>(){
			private Map flightAttrbute;
			public void rowdata(String key,String value, Map o) {
				if("Status".equals(key)){
					o.put("STATUS", value);
				}else if ("Msg".equals(key)) {
					o.put("MSG", value);
				}	
			}
		});
		return avhResponse;
	}
	
	public static void main(String[] args){	
		LuoJiaServiceImpl l = new LuoJiaServiceImpl();
		String url = "";
		Map m = new HashMap();
		m.put("account","TY");
		m.put("pwd","ty001");
		m.put("Dpt","SHA");  
		m.put("Arr","DLC");
		m.put("Date","2013-12-06");
		m.put("ArrLine","CZ");

		try {
			System.getProperties().setProperty("proxySet", "true"); // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
			System.getProperties().setProperty("http.proxyHost", "10.8.15.118");
			System.getProperties().setProperty("http.proxyPort", "606");
			Map avhResponse = l.avh("http://open.elufei.com/Service.asmx/AVH",m);
			
			
			//System.out.println(JsonMapper.getInstance().toJson(avhResponse));
			Map r = JsonMapper.getInstance().fromJson(JsonMapper.getInstance().toJson(avhResponse), Map.class);
			//System.out.println(r.get("RETURN_CODE"));
			//System.out.println(((List)((Map)((List)((Map)r.get("DATALIST")).get("FLIGHTS")).get(0)).get("CABINS")).get(0));
			System.out.println((Map)r.get("DATALIST"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
