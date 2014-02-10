package com.whty.platform.modules.luojia.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whty.platform.common.config.Global;
import com.whty.platform.common.utils.mapper.JsonMapper;
import com.whty.platform.modules.bussiness.entity.Provider;
import com.whty.platform.modules.bussiness.entity.Services;
import com.whty.platform.modules.bussiness.service.OrderRecordService;
import com.whty.platform.modules.luojia.entity.AVHRequest;
import com.whty.platform.modules.luojia.entity.CreateOrderRequest;
import com.whty.platform.modules.luojia.entity.CreateOrderRequestInput;
import com.whty.platform.modules.luojia.entity.CreateOrderRequestPassenger;
import com.whty.platform.modules.luojia.entity.CreateOrderRequestSegment;
import com.whty.platform.modules.luojia.entity.SearchOrderRequest;
import com.whty.platform.modules.luojia.service.LuoJiaDBService;
import com.whty.platform.modules.luojia.utils.LuoJiaVerify;
import com.whty.platform.modules.sp.entity.HttpBusinessRequest;
import com.whty.platform.modules.sp.entity.HttpBusinessResponse;
import com.whty.platform.modules.sp.entity.SpConsts;
import com.whty.platform.modules.sp.handler.HttpBusinessHandler;
import com.whty.platform.modules.sp.utils.ActinoInfoUtils;
import com.whty.platform.modules.sp.utils.DecodeUtils;

@Service("luoJiaHttpHandler")
public class LuoJiaHttpHandler implements HttpBusinessHandler {
	private static Logger logger = LoggerFactory.getLogger(LuoJiaHttpHandler.class);

	@Autowired
	private ILuoJiaHandler luoJiaHandler;
	
	@Autowired
	private LuoJiaDBService luoJiaDBService;
	

	@SuppressWarnings("rawtypes")
	public HttpBusinessResponse handler(HttpBusinessRequest request) {
		Services services = request.getServices();
		String key = request.getKey();
		// 取得URL
		StringBuffer interFaceUrl = new StringBuffer("http://");
		Provider provider = services.getProvider();
		interFaceUrl.append(provider.getIp());
		interFaceUrl.append(":").append(provider.getPort());
		interFaceUrl.append(services.getUri());
		logger.debug(interFaceUrl.toString());

		// 返回请求数据装配
		HttpBusinessResponse httpResponse = new HttpBusinessResponse();
		httpResponse.setActionName(request.getActionName());

		try {
			if (SpConsts.SELECT_PRODUCT.equals(request.getActionName())) {
				boolean rt = LuoJiaVerify.verifyAVHRequest(request, httpResponse, key); 
				if (rt) {
					Map<String, Object> aInfo = ActinoInfoUtils.info2map(request.getActionInfo(), key);
					logger.info("ActionInfo=" + aInfo);
					String dpt = (String) aInfo.get("DEP");
					String arr = (String) aInfo.get("ARR");
					String date = (String) aInfo.get("DATE");
					String airCo = (String) aInfo.get("AIR_CO");
					AVHRequest rq = new AVHRequest(Global.getConfig("luojia.account"),Global.getConfig("luojia.pwd"),dpt, arr, date, airCo);
					Map<String, Object> rsp = luoJiaHandler.avh(rq);
					if (rsp.get("RETURN_CODE").equals("0000")) {
						httpResponse.addActionValue("ACTION_RETURN_CODE", SpConsts.ACTION_RETURN_CODE_000000);
						Map dataList = (Map) rsp.get("DATALIST");
						List responseActionInfo = parseAvhResponse(dataList);
						String rejson = JsonMapper.getInstance().toJson(responseActionInfo);
						logger.debug(rejson);
						httpResponse.addActionValue("ACTION_INFO", DecodeUtils.deData(rejson, key));
						httpResponse.setStatus(SpConsts.SUSSCCE);
					} else {
						httpResponse.addActionValue("ACTION_RETURN_CODE", "100048");
						httpResponse.addActionValue("MESSAGE", rsp.get("ERROR"));
						httpResponse.setStatus("2");
					}
				}
			} else if (SpConsts.CREATE_ORDER.equals(request.getActionName())) {
				boolean iRt = false;
				boolean rt = LuoJiaVerify.verifyCreateOrderRequest(request, httpResponse, key);
				if(rt){
					Map<String, Object> aInfo = ActinoInfoUtils.info2map(request.getActionInfo(), key);
					logger.info("ActionInfo=" + aInfo);
					Map<String, Object> rsp = luoJiaHandler.createOrder(newCreateOrderRequest(aInfo));
					if (rsp.get("STATUS").equals("1")) {
						String orderId = (String)rsp.get("ORDERNO");
						httpResponse.addActionValue("ACTION_RETURN_CODE", SpConsts.ACTION_RETURN_CODE_000000);
						Map actionInfo = new HashMap();
						actionInfo.put("ORDER_ID", orderId);
						actionInfo.put("CUSTOMER_ORDER_NO", aInfo.get("CUSTOMER_ORDER_NO"));
						String rejson = JsonMapper.getInstance().toJson(actionInfo);
						logger.debug(rejson);
						httpResponse.addActionValue("ACTION_INFO", DecodeUtils.deData(rejson, key));
						httpResponse.setStatus(SpConsts.SUSSCCE);
						iRt = true;						
					}else {
						httpResponse.addActionValue("ACTION_RETURN_CODE", "100048");
						httpResponse.addActionValue("MESSAGE", rsp.get("MSG"));
						httpResponse.setStatus("2");
					}
					if(iRt){
						try{
							luoJiaDBService.createOrder(request, aInfo, rsp);
							//luoJiaDBService.createFlightOrder(aInfo, rsp);
						}catch(Exception e){
							httpResponse.addActionValue("MESSAGE", "机票订购成功，写数据库失败。");
							logger.error(e.getMessage(), e);
						}
					}					
				}
			}else if (SpConsts.SELECT_PRODUCT2.equals(request.getActionName())) {
				boolean rt = LuoJiaVerify.verifyCheckOrderRequest(request, httpResponse, key);
				if(rt){
					Map<String, Object> aInfo = ActinoInfoUtils.info2map(request.getActionInfo(), key);
					logger.info("ActionInfo=" + aInfo);
					Map<String, Object> rsp = luoJiaHandler.checkOrder((String)aInfo.get("ORDER_ID"));
					httpResponse.addActionValue("ACTION_RETURN_CODE", SpConsts.ACTION_RETURN_CODE_000000);
					String rejson = JsonMapper.getInstance().toJson(rsp);
					logger.debug(rejson);
					httpResponse.addActionValue("ACTION_INFO", DecodeUtils.deData(rejson, key));
					httpResponse.setStatus(SpConsts.SUSSCCE);					
				}
			}else if (SpConsts.SELECT_PRODUCT3.equals(request.getActionName())) {
				boolean rt = LuoJiaVerify.verifySearchOrderRequest(request, httpResponse, key);
				if(rt){
					Map<String, Object> aInfo = ActinoInfoUtils.info2map(request.getActionInfo(), key);
					logger.info("ActionInfo=" + aInfo);
					SearchOrderRequest r = new SearchOrderRequest(Global.getConfig("luojia.account"),Global.getConfig("luojia.pwd"),(String)aInfo.get("ORDER_ID"));
					Map<String, Object> rsp = luoJiaHandler.searchOrder(r);
					httpResponse.addActionValue("ACTION_RETURN_CODE", SpConsts.ACTION_RETURN_CODE_000000);
					String rejson = JsonMapper.getInstance().toJson(rsp);
					logger.debug(rejson);
					httpResponse.addActionValue("ACTION_INFO", DecodeUtils.deData(rejson, key));
					httpResponse.setStatus(SpConsts.SUSSCCE);					
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			httpResponse.addActionValue("ACTION_RETURN_CODE", "100048");
			httpResponse.addActionValue("MESSAGE", e.getMessage());
			httpResponse.setStatus("2");
		}
		logger.info("httpResponse=" + JsonMapper.getInstance().toJson(httpResponse));
		return httpResponse;
	}	

	private List parseAvhResponse(Map dataList) {
		List fs = new ArrayList();
		if (dataList != null) {
			List<Map> flights = (List) dataList.get("FLIGHTS");
			if (flights != null && flights.size() > 0) {
				for (Map flight : flights) {
					Map f = new HashMap();
					List<Map> cabins = (List) flight.get("CABINS");
					String count = "0";
					List cs = null;
					if (cabins != null && cabins.size() > 0) {
						cs = new ArrayList();
						for (Map cabin : cabins) {
							Map c = new HashMap();
							c.put("SEAT", cabin.get("CODE"));
							c.put("ZK", cabin.get("ZK"));
							c.put("PRICE_SALE", cabin.get("PRICE"));
							String status = (String) cabin.get("STATUS");
							if (count.equals("0") || !count.equals("A")) {
								if (status.equals("A")) {
									count = "A";
								} else if (status.equals("S") || status.equals("Q")) {
									// int m = Integer.parseInt(count);
									// count = "0";
								} else {
									int m = Integer.parseInt(count);
									int k = Integer.parseInt(status);
									if (m + k > 9) {
										count = "A";
									} else {
										count = String.valueOf(m + k);
									}
								}
							}
							c.put("COUNT", cabin.get("STATUS"));
							c.put("SCGD", cabin.get("SCGD"));
							c.put("TPGD", cabin.get("TPGD"));
							c.put("QZGD", cabin.get("QZGD"));
							c.put("ZCID", cabin.get("ZCID"));
							cs.add(c);
						}
					}
					f.put("FLIGHT", flight.get("CODE"));
					f.put("CARRIER", flight.get("CARRIER"));
					f.put("DPT", dataList.get("DPT"));
					f.put("ARR", dataList.get("ARR"));
					f.put("PTYPE", flight.get("PLANTYPE"));
					f.put("GO_TIME", flight.get("DPTTIME"));
					f.put("TO_TIME", flight.get("ARRTIME"));
					f.put("IS_JT", flight.get("CODESHARE"));
					f.put("RY", flight.get("TAX"));
					f.put("JJ", flight.get("AIRTAX"));
					f.put("ZK", flight.get("LOWZK"));// 不确定
					f.put("PRICE_STAND", flight.get("YPRICE"));// 不确定
					f.put("PRICE_SALE", flight.get("PRICE"));// 不确定
					f.put("COUNT", count);
					f.put("EXP", flight.get("PRICE"));
					f.put("INSURE", flight.get("PRICE"));
					f.put("DATA", cs);
					fs.add(f);
				}
			}
		}
		return fs;
	}

	private CreateOrderRequest newCreateOrderRequest(Map actionInfo) {
		CreateOrderRequest createOrderRequest = new CreateOrderRequest();
		CreateOrderRequestInput input = new CreateOrderRequestInput();
		String other = (String)actionInfo.get("OTHER");
		if(other.endsWith("|")) other += " ";
		String[] others = other.split("\\|");
		
		String tripType = others[0];
		String dptDate = others[1];
		String tsrDate = others[2];
		String bakDate = others[3];
		String adultNumber = others[4];
		String chdNumber = others[5];
		
		input.setDptDate(dptDate);
		input.setTsrDate(tsrDate);
		input.setBakDate(bakDate);
		input.setTripType(tripType);		
		input.setAdultNumber(adultNumber);
		input.setChdNumber(chdNumber);
		
		input.setIssue("1");			
		
		String delivery = (String)actionInfo.get("DELIVERY");
		if(delivery.endsWith("|")) delivery += " ";
		String[] deliverys = delivery.split("\\|");

		String deliveryType = deliverys[0];		
		String deliveryPrice = deliverys[8];		
		input.setDeliveryType(deliveryType);
		
		if(deliveryType.equals("2")){
			String deliveryDate = deliverys[1];
			String deliveryTime = deliverys[2];
			String deliveryAddress = deliverys[3];
			String deliveryCity = deliverys[4];
			input.setDeliveryDate(deliveryDate);
			input.setDeliveryTime(deliveryTime);
			input.setDeliveryAddress(deliveryAddress);			
			input.setDeliveryCity(deliveryCity);
		}else if(deliveryType.equals("3")){
			String selfGetDate = deliverys[5];
			String selfGetTime = deliverys[6];
			input.setSelfGetDate(selfGetDate);
			input.setSelfGetTime(selfGetTime);
		}else if(deliveryType.equals("4")){
			String isProvince = deliverys[7];
			String selfGetDate = deliverys[5];
			String selfGetTime = deliverys[6];
			input.setIsProvince(isProvince);
			input.setSelfGetDate(selfGetDate);
			input.setSelfGetTime(selfGetTime);
		}
		
		input.setInsurancePrice("20");	
		
		String contactInfo = (String)actionInfo.get("CONTACT_INFO");
		if(contactInfo.endsWith("|")) contactInfo += " ";
		String[] contactInfos = contactInfo.split("\\|");
		String contactName = contactInfos[0];
		String contactMobile = contactInfos[1];
		String contactTel = contactInfos[2];
		String email = contactInfos[3];
		input.setContactName(contactName);
		input.setContactMobile(contactMobile);
		input.setContactTel(contactTel);
		input.setEmail(email);		
		
		String expInfo = (String)actionInfo.get("EXP_INFO");
		if(expInfo.endsWith("|")) expInfo += " ";
		String[] expInfos = expInfo.split("\\|");
		String route = expInfos[0];
		String recipient = expInfos[1];
		String rcptAddress = expInfos[2];
		String post = expInfos[3];
		String postPrice = expInfos[4];
		
		input.setRoute(route);
		input.setPostPrice(postPrice);
		input.setRecipient(recipient);
		input.setPost(post);
		input.setRcptAddress(rcptAddress);
		
		String remark = (String)actionInfo.get("REMARK");
		input.setRemark(remark);
		
		String flightsInfo = (String)actionInfo.get("FLIGHT_INFO");		
		if(tripType.equals("1")){
			if(flightsInfo.endsWith("|")) flightsInfo += " ";
			String[] flightInfos = flightsInfo.split("\\|");
			String airLine = flightInfos[0];
			String planType = flightInfos[1];
			String dpt = flightInfos[2];
			String dptName = flightInfos[3];
			String arr = flightInfos[4];
			String arrName = flightInfos[5];
			String dptTime = flightInfos[6];
			String arrTime = flightInfos[7];
			String code = flightInfos[8];
			String discount = flightInfos[9];
			String carrier = flightInfos[10];
			CreateOrderRequestSegment segment = new CreateOrderRequestSegment();
			segment.setDpt(dpt);
			segment.setArr(arr);
			segment.setDptTime(dptTime);
			segment.setArrTime(arrTime);
			segment.setAirLine(airLine);
			segment.setCarrier(carrier);
			segment.setDiscount(discount);
			segment.setCode(code);
			segment.setPlanType(planType);
			input.setSegments(segment);
		}else{
			String[] flightsInfos = flightsInfo.split("\\^");
			if(flightsInfos != null && flightsInfos.length > 0){
				for(String flightInfos:flightsInfos){
					if(flightInfos.endsWith("|")) flightInfos += " ";
					String flightInfo[] = flightInfos.split("\\|");
					String airLine = flightInfo[0];
					String planType = flightInfo[1];
					String dpt = flightInfo[2];
					String dptName = flightInfo[3];
					String arr = flightInfo[4];
					String arrName = flightInfo[5];
					String dptTime = flightInfo[6];
					String arrTime = flightInfo[7];
					String code = flightInfo[8];
					String discount = flightInfo[9];
					String carrier = flightInfo[10];
					CreateOrderRequestSegment segment = new CreateOrderRequestSegment();
					segment.setDpt(dpt);
					segment.setArr(arr);
					segment.setDptTime(dptTime.split(":")[0] + dptTime.split(":")[1]);
					segment.setArrTime(arrTime.split(":")[0] + arrTime.split(":")[1]);
					segment.setAirLine(airLine);
					segment.setCarrier(carrier);
					segment.setDiscount(discount);
					segment.setCode(code);
					segment.setPlanType(planType);
					input.setSegments(segment);
				}
			}
		}		
		
		String passengersInfo = (String)actionInfo.get("PASSENGER_INFO");
		if(passengersInfo.endsWith("|")) passengersInfo += " ";
		String[] passengersInfos = passengersInfo.split("\\^");		
		if(passengersInfos != null && passengersInfos.length > 0){
			for(String passengerInfos:passengersInfos){
				if(passengerInfos.endsWith("|")) passengerInfos += " ";
				String[] passengerInfo = passengerInfos.split("\\|");
				String passengerType = passengerInfo[0];
				String name = passengerInfo[1];
				String certificateType = passengerInfo[2];
				String certificateNumber = passengerInfo[3];
				String price = passengerInfo[4];
				String tax = passengerInfo[5];
				String airTax = passengerInfo[6];
				String insuranceDeal = passengerInfo[7];
				String price1 = passengerInfo[8];
				String tax1 = passengerInfo[9];
				String airTax1 = passengerInfo[10];
				String csrq = passengerInfo[11];
				CreateOrderRequestPassenger passenger = new CreateOrderRequestPassenger();
				passenger.setName(name);
				passenger.setCertificateType(certificateType);
				passenger.setCertificateNumber(certificateNumber);
				passenger.setPassengerType(passengerType);
				passenger.setCsrq(csrq);
				passenger.setInsuranceDeal(insuranceDeal);
				passenger.setPrice(price);
				passenger.setAirTax(airTax);
				passenger.setTax(tax);
				input.setPassengers(passenger);
			}
		}
		
		createOrderRequest.setAccount(Global.getConfig("luojia.account"));
		createOrderRequest.setPwd(Global.getConfig("luojia.pwd"));
		createOrderRequest.setInput(input);
		return createOrderRequest;
	}

	public static void main(String[] args) {
		System.getProperties().setProperty("proxySet", "true"); // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
		System.getProperties().setProperty("http.proxyHost", "10.8.15.118");
		System.getProperties().setProperty("http.proxyPort", "606");
		LuoJiaHttpHandler handler = new LuoJiaHttpHandler();
		Map r = new HashMap();
				
		Map aInfo = new HashMap();
		r.put("ACTION_NAME", "CREATE_ORDER");
		r.put("APP_ID", "402");
		r.put("ACTION_USER", "HSQ");
		aInfo.put("USER_ID", "12060518062813200001");
		aInfo.put("TRANS_TYPE", "0");
		aInfo.put("FLIGHT_INFO", "MU2525|EMB|WUH|武汉|DLC|广州|16:10|19:30|R|0.40|CA^MU2525|EMB|WUH|武汉|DLC|广州|16:10|19:30|R|0.40|CA");
		aInfo.put("CONTACT_INFO", "测试|18980023507|87553321|");
		aInfo.put("PASSENGER_INFO", "1|李宗瑞|NI|5132322318347247|1300|130|50|2|1200|130|50^1||李宗仁| NI |4132322318347247|1300|130|50|2|1200|130|50|");
		aInfo.put("EXP_INFO", "0|||");
		aInfo.put("DELIVERY", "1||||||");
		aInfo.put("OTHER", "2|2012-09-01||2012-09-05|2|0");
		aInfo.put("REMARK", "我勒个去！");
		aInfo.put("AMOUNT", "100000");
		aInfo.put("AMOUNT_DETAIL", "0|280|150|50|0");
		r.put("ACTION_INFO", aInfo);
		
		String json= JsonMapper.getInstance().toJson(r);
		System.out.println(json);
		Map m = JsonMapper.getInstance().fromJson(json, Map.class);
		
		
		//System.out.println(m.get("ACTION_INFO"));
		try {
			//HttpBusinessRequest avhRequest = new HttpBusinessRequest(JsonMapper.getInstance().toJson(r));
			//HttpBusinessResponse rep = handler.handler(avhRequest);
			// System.out.println("ReplyMessage=" + rep.getReplyMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
