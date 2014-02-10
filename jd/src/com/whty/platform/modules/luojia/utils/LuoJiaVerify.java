package com.whty.platform.modules.luojia.utils;

import java.util.HashMap;
import java.util.Map;

import com.whty.platform.modules.qindong.utils.HttpQDUtils;
import com.whty.platform.modules.sp.entity.HttpBusinessRequest;
import com.whty.platform.modules.sp.entity.HttpBusinessResponse;
import com.whty.platform.modules.sp.entity.SpConsts;
import com.whty.platform.modules.sp.utils.ActinoInfoUtils;

public final class LuoJiaVerify {
	public static boolean verifyCreateOrderRequest(HttpBusinessRequest request,HttpBusinessResponse httpBusinessResponse,String key) throws Exception{
		String actionName = request.getActionName();
		boolean rt = verify(httpBusinessResponse,actionName,"100004","ACTION_NAME IS NULL");
		if(!rt) return rt;
		String appId = request.getAppId();
		rt = verifyAppId(httpBusinessResponse,appId,"100004","APP_ID IS NULL","402");
		if(!rt) return rt;
		String actionUser = request.getActionUser();
		rt = verify(httpBusinessResponse,actionUser,"100004","ACTION_USER IS NULL");
		if(!rt) return rt;
		String actionInfo = request.getActionInfo();
		rt = verify(httpBusinessResponse,actionInfo,"100004","ACTION_INFO IS NULL");
		if(!rt) return rt;
		Map<String, Object> aInfo = ActinoInfoUtils.info2map(request.getActionInfo(), key);
		String userId = (String)aInfo.get("USER_ID");
		rt = verify(httpBusinessResponse,userId,"100004","USER_ID IS NULL");
		if(!rt) return rt;
		String transType = (String)aInfo.get("TRANS_TYPE");
		rt = verify(httpBusinessResponse,transType,"100004","TRANS_TYPE IS NULL");
		if(!rt) return rt;
		String flight = (String)aInfo.get("FLIGHT_INFO");		
		rt = flightInfo(httpBusinessResponse,flight,"100004","FLIGHT_INFO IS NULL");
		if(!rt) return rt;
		String contactInfo = (String)aInfo.get("CONTACT_INFO");
		rt = contactInfo(httpBusinessResponse,contactInfo,"100004","CONTACT_INFO IS NULL");
		if(!rt) return rt;
		String passengerInfo = (String)aInfo.get("PASSENGER_INFO");
		rt = passengerInfo(httpBusinessResponse,passengerInfo,"100004","PASSENGER_INFO IS NULL");
		if(!rt) return rt;
		String expInfo = (String)aInfo.get("EXP_INFO");
		rt = expInfo(httpBusinessResponse,expInfo,"100004","EXP_INFO IS NULL");
		if(!rt) return rt;
		String delivery = (String)aInfo.get("DELIVERY");
		rt = delivery(httpBusinessResponse,delivery,"100004","DELIVERY IS NULL");
		if(!rt) return rt;
		String other = (String)aInfo.get("OTHER");
		rt = other(httpBusinessResponse,other,"100004","OTHER IS NULL");
		if(!rt) return rt;
		String amount = (String)aInfo.get("AMOUNT");
		rt = verify(httpBusinessResponse,amount,"100004","AMOUNT IS NULL");
		if(!rt) return rt;
		String amountDetail = (String)aInfo.get("AMOUNT_DETAIL");
		rt = amountDetail(httpBusinessResponse,amountDetail,"100004","AMOUNT_DETAIL IS NULL");
		if(!rt) return rt;
		String customerOrderNo = (String)aInfo.get("CUSTOMER_ORDER_NO");
		rt = verify(httpBusinessResponse,customerOrderNo,"100004","CUSTOMER_ORDER_NO IS NULL");
		if(!rt) return rt;
		return true;
	}
	
	public static boolean verifyAVHRequest(HttpBusinessRequest request,HttpBusinessResponse httpBusinessResponse,String key) throws Exception{
		String actionName = request.getActionName();
		boolean rt = verify(httpBusinessResponse,actionName,"100004","ACTION_NAME IS NULL");
		if(!rt) return rt;
		String appId = request.getAppId();
		rt = verifyAppId(httpBusinessResponse,appId,"100004","APP_ID IS NULL","402");
		if(!rt) return rt;
		String actionUser = request.getActionUser();
		rt = verify(httpBusinessResponse,actionUser,"100004","ACTION_USER IS NULL");
		if(!rt) return rt;
		String actionInfo = request.getActionInfo();
		rt = verify(httpBusinessResponse,actionInfo,"100004","ACTION_INFO IS NULL");
		if(!rt) return rt;
		Map<String, Object> aInfo = ActinoInfoUtils.info2map(request.getActionInfo(), key);
		String dpt = (String)aInfo.get("DEP");
		rt = verify(httpBusinessResponse,dpt,"100004","DEP IS NULL");
		if(!rt) return rt;
		String arr = (String)aInfo.get("ARR");
		rt = verify(httpBusinessResponse,arr,"100004","ARR IS NULL");
		if(!rt) return rt;
		String date = (String)aInfo.get("DATE");
		rt = verify(httpBusinessResponse,date,"100004","DATE IS NULL");
		if(!rt) return rt;
//		String airCo = (String)aInfo.get("AIR_CO");
//		rt = verify(httpBusinessResponse,airCo,"100004","AIR_CO IS NULL");
//		if(!rt) return rt;
		return true;
	}
	
	public static boolean verifyCheckOrderRequest(HttpBusinessRequest request,HttpBusinessResponse httpBusinessResponse,String key) throws Exception{
		String actionName = request.getActionName();
		boolean rt = verify(httpBusinessResponse,actionName,"100004","ACTION_NAME IS NULL");
		if(!rt) return rt;
		String appId = request.getAppId();
		rt = verifyAppId(httpBusinessResponse,appId,"100004","APP_ID IS NULL","402");
		if(!rt) return rt;
		String actionUser = request.getActionUser();
		rt = verify(httpBusinessResponse,actionUser,"100004","ACTION_USER IS NULL");
		if(!rt) return rt;
		String actionInfo = request.getActionInfo();
		rt = verify(httpBusinessResponse,actionInfo,"100004","ACTION_INFO IS NULL");
		if(!rt) return rt;
		Map<String, Object> aInfo = ActinoInfoUtils.info2map(request.getActionInfo(), key);
		String orderId = (String)aInfo.get("ORDER_ID");
		rt = verify(httpBusinessResponse,orderId,"100004","ORDER_ID IS NULL");
		if(!rt) return rt;
		return true;
	}
	
	public static boolean verifySearchOrderRequest(HttpBusinessRequest request,HttpBusinessResponse httpBusinessResponse,String key) throws Exception{
		String actionName = request.getActionName();
		boolean rt = verify(httpBusinessResponse,actionName,"100004","ACTION_NAME IS NULL");
		if(!rt) return rt;
		String appId = request.getAppId();
		rt = verifyAppId(httpBusinessResponse,appId,"100004","APP_ID IS NULL","402");
		if(!rt) return rt;
		String actionUser = request.getActionUser();
		rt = verify(httpBusinessResponse,actionUser,"100004","ACTION_USER IS NULL");
		if(!rt) return rt;
		String actionInfo = request.getActionInfo();
		rt = verify(httpBusinessResponse,actionInfo,"100004","ACTION_INFO IS NULL");
		if(!rt) return rt;
		Map<String, Object> aInfo = ActinoInfoUtils.info2map(request.getActionInfo(), key);
		String orderId = (String)aInfo.get("ORDER_ID");
		rt = verify(httpBusinessResponse,orderId,"100004","ORDER_ID IS NULL");
		if(!rt) return rt;
		return true;
	}
	
	public final static boolean amountDetail(HttpBusinessResponse response,String value,String code,String msg){
		boolean rt = verify(response,value,code,msg);
		if(rt){
			if(value.endsWith("|")) value += " ";
			String[] as = value.split("\\|");
			if(as == null || as.length < 5){
				response.addActionValue("ACTION_RETURN_CODE", code);
				response.addActionValue("MESSAGE", "AMOUNT_DETAIL IS ERROR");
				response.setStatus("2");
				rt = false;
			}
		}
		return rt;
	}
	
	public final static boolean other(HttpBusinessResponse response,String value,String code,String msg){
		boolean rt = verify(response,value,code,msg);
		if(rt){
			if(value.endsWith("|")) value += " ";
			String[] os = value.split("\\|");
			if(os == null || os.length < 6){
				response.addActionValue("ACTION_RETURN_CODE", code);
				response.addActionValue("MESSAGE", "OTHER IS ERROR");
				response.setStatus("2");
				rt = false;
			}
		}
		return rt;
	}
	
	public final static boolean delivery(HttpBusinessResponse response,String value,String code,String msg){
		boolean rt = verify(response,value,code,msg);
		if(rt){
			if(value.endsWith("|")) value += " ";
			String[] es = value.split("\\|");
			if(es == null || es.length < 9){
				response.addActionValue("ACTION_RETURN_CODE", code);
				response.addActionValue("MESSAGE", "DELIVERY IS ERROR");
				response.setStatus("2");
				rt = false;
			}
		}
		return rt;
	}
	
	public final static boolean expInfo(HttpBusinessResponse response,String value,String code,String msg){
		boolean rt = verify(response,value,code,msg);
		if(rt){
			if(value.endsWith("|")) value += " ";
			String[] es = value.split("\\|");
			if(es == null || es.length < 4){
				response.addActionValue("ACTION_RETURN_CODE", code);
				response.addActionValue("MESSAGE", "EXP_INFO IS ERROR");
				response.setStatus("2");
				rt = false;
			}
		}
		return rt;
	}
	
	public final static boolean passengerInfo(HttpBusinessResponse response,String value,String code,String msg){
		boolean rt = verify(response,value,code,msg);
		if(rt){
			
			if(value.contains("^")){
				String[] pInfos = value.split("\\^");
				for(String p:pInfos){
					if(p.endsWith("|")) p += " ";
					String[] ps = p.split("\\|");
					if(ps == null || ps.length < 12){
						response.addActionValue("ACTION_RETURN_CODE", code);
						response.addActionValue("MESSAGE", "PASSENGER_INFO IS ERROR");
						response.setStatus("2");
						rt = false;
						break;
					}
				}
			}else {
				if(value.endsWith("|")) value += " ";
				String[] ps = value.split("\\|");
				if(ps == null || ps.length < 12){
					response.addActionValue("ACTION_RETURN_CODE", code);
					response.addActionValue("MESSAGE", "PASSENGER_INFO IS ERROR");
					response.setStatus("2");
					rt = false;
				}
			}
		}
		return rt;
	}
	
	public final static boolean contactInfo(HttpBusinessResponse response,String value,String code,String msg){
		boolean rt = verify(response,value,code,msg);
		if(rt){
			if(value.endsWith("|")) value += " ";
			String[] cs = value.split("\\|");
			if(cs == null || cs.length < 4){
				response.addActionValue("ACTION_RETURN_CODE", code);
				response.addActionValue("MESSAGE", "CONTACT_INFO IS ERROR");
				response.setStatus("2");
				rt = false;
			}
		}
		return rt;
	}
	
	public final static boolean flightInfo(HttpBusinessResponse response,String value,String code,String msg){
		boolean rt = verify(response,value,code,msg);
		if(rt){
			if(value.contains("^")){
				String[] filights = value.split("\\^");
				for(String f:filights){
					if(f.endsWith("|")) f += " ";
					String[] fs = f.split("\\|");
					if(fs == null || fs.length < 11){
						response.addActionValue("ACTION_RETURN_CODE", code);
						response.addActionValue("MESSAGE", "FLIGHT_INFO IS ERROR");
						response.setStatus("2");
						rt = false;
						break;
					}
				}
			}else {
				if(value.endsWith("|")) value += " ";
				String[] fs = value.split("\\|");
				if(fs == null || fs.length < 11){
					response.addActionValue("ACTION_RETURN_CODE", code);
					response.addActionValue("MESSAGE", "FLIGHT_INFO IS ERROR");
					response.setStatus("2");
					rt = false;
				}
			}
		}
		return rt;
	}	
	
	public final static boolean verify(HttpBusinessResponse response,String value,String code,String msg){
		if(value == null || value.equals("")){
			response.addActionValue("ACTION_RETURN_CODE", code);
			response.addActionValue("MESSAGE", msg);
			response.setStatus("2");
			return false;
		}
		return true;
	}	
	
	public final static boolean verifyAppId(HttpBusinessResponse response,String value,String code,String msg,String appId){
		boolean rt = verify(response,value,code,msg);
		if(rt){
			if(!value.equals(appId)){
				response.addActionValue("ACTION_RETURN_CODE", code);
				response.addActionValue("MESSAGE", "APP_ID IS ERROR");
				response.setStatus("2");
				rt = false;
			}
		}
		return rt;
	}
	
	public final static boolean verify(HttpBusinessResponse response,Map value,String code,String msg){
		if(value == null){
			response.addActionValue("ACTION_RETURN_CODE", code);
			response.addActionValue("MESSAGE", msg);
			response.setStatus("2");
			return false;
		}
		return true;
	}
	
	public static void main(String[] args){
		System.out.println("112^2".contains("\\^"));
	}
}
