package com.whty.platform.modules.luojia.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.whty.platform.common.utils.mapper.JsonMapper;
import com.whty.platform.modules.luojia.entity.AVHRequest;
import com.whty.platform.modules.luojia.entity.CreateOrderRequest;
import com.whty.platform.modules.luojia.entity.CreateOrderRequestInput;
import com.whty.platform.modules.luojia.entity.CreateOrderRequestPassenger;
import com.whty.platform.modules.luojia.entity.CreateOrderRequestSegment;
import com.whty.platform.modules.luojia.entity.SearchOrderRequest;
import com.whty.platform.modules.luojia.service.ILuoJiaService;
import com.whty.platform.modules.luojia.service.LuoJiaServiceImpl;

@Service("luoJiaHandler")
public class LuoJiaHandler implements ILuoJiaHandler{
	private static Logger logger = LoggerFactory.getLogger(LuoJiaHandler.class);
	
	@Autowired
	private ILuoJiaService luoJiaService;
	private final static String AVH_URL = "http://open.elufei.com/Service.asmx/AVH";
	private final static String ORDER_URL = "http://open.elufei.com/Service.asmx/CreateOrder";	
	private final static String SEARCH_ORDER_URL = "http://open.elufei.com/Service.asmx/SearchOrder";
	private final static String CHECK_ORDER_URL = "http://open.elufei.com/Service.asmx/CheckOrder";
	
	public Map avh(AVHRequest request) {
		Map m = new HashMap();
		m.put("account", request.getAccount());
		m.put("pwd", request.getPwd());
		m.put("Dpt", request.getDpt());
		m.put("Arr", request.getArr());
		m.put("Date", request.getDate());
		m.put("ArrLine", request.getArrLine());
		Map response = null;
		try {
			response = luoJiaService.avh(AVH_URL, m);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			response.put("ERROR", "系统忙，请稍后再试。");
			response.put("RETURN_CODE", "0002");
		}
		return response;
	}
	
	public Map createOrder(CreateOrderRequest request) {		
		
		Map response = null;
		try {
			Map m = new HashMap();
			m.put("input", request.getInputToXml());
			m.put("account", request.getAccount());
			m.put("pwd", request.getPwd());
			response = luoJiaService.createOrder(ORDER_URL, m);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			response.put("MSG", "系统忙，请稍后再试。");
			response.put("STATUS", "0");
		}
		return response;
	}
	
	public Map searchOrder(SearchOrderRequest request) {		
		Map m = new HashMap();			
		m.put("account", request.getAccount());
		m.put("pwd", request.getPwd());
		m.put("OrderID", request.getOrderId());
		Map response = null;
		try {
			response = luoJiaService.searchOrder(SEARCH_ORDER_URL, m);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			response.put("MSG", "系统忙，请稍后再试。");
			response.put("STATUS", "0");
		}
		return response;
	}
	
	public Map checkOrder(String orderId) {		
		Map m = new HashMap();			
		m.put("oid", orderId);
		Map response = null;
		try {
			response = luoJiaService.checkOrder(CHECK_ORDER_URL, m);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			response.put("MSG", "系统忙，请稍后再试。");
			response.put("STATUS", "0");
		}
		return response;
	}
	
	
	public static void main(String[] args){	
		LuoJiaHandler l = new LuoJiaHandler();
		String url = "";
		AVHRequest request = new AVHRequest();
		request.setDpt("SHA");  
		request.setArr("DLC");
		request.setDate("2013-11-06");
		request.setArrLine("CZ");
		
		Map r = new HashMap();
		
		Map aInfo = new HashMap();
		r.put("ACTION_NAME", "CREATE_ORDER");
		r.put("APP_ID", "402");
		r.put("ACTION_USER", "HSQ");
		aInfo.put("USER_ID", "12060518062813200001");
		aInfo.put("TRANS_TYPE", "0");
		aInfo.put("FLIGHT_INFO", "CZ369|738|WUH|武汉|SHA|上海|12:40|14:10|R|0.51|CZ^CZ369|738|WUH|武汉|SHA|上海|12:40|14:10|R|0.51|CZ");
		aInfo.put("CONTACT_INFO", "许圣华|13545191747||xushenghua130@163.com");
		aInfo.put("PASSENGER_INFO", "1|许圣华|0|421083198710213278|530|70|50|1|0|0|0|2013-11-28^1|宋亮|0|421083198710213278|530|70|50|1|0|0|0|2013-11-28");
		aInfo.put("EXP_INFO", "1|许圣华|湖北武汉雄楚大道|430000|30");
		aInfo.put("DELIVERY", "1||||||||");
		aInfo.put("OTHER", "1|2013-11-29|||2|0");
		aInfo.put("REMARK", "");
		aInfo.put("AMOUNT", "1000");
		aInfo.put("AMOUNT_DETAIL", "1|1780|100|140|20");
		aInfo.put("CUSTOMER_ORDER_NO", "20131127165051831979");
		r.put("ACTION_INFO", aInfo);
		
		String json1= JsonMapper.getInstance().toJson(aInfo);
		System.out.println(json1);
		
		
		CreateOrderRequest createOrderRequest = new CreateOrderRequest();
		CreateOrderRequestInput input = new CreateOrderRequestInput();
		String json= JsonMapper.getInstance().toJson(r);
		System.out.println(json);
		Map m = JsonMapper.getInstance().fromJson(json, Map.class);
		
		Map m1 = (Map)m.get("ACTION_INFO");
		String other = (String)m1.get("OTHER");
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
		
		String delivery = (String)m1.get("DELIVERY");
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
		
		String contactInfo = (String)m1.get("CONTACT_INFO");
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
		
		String expInfo = (String)m1.get("EXP_INFO");
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
		
		String remark = (String)m1.get("REMARK");
		input.setRemark(remark);
		
		String flightsInfo = (String)m1.get("FLIGHT_INFO");		
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
			if(flightsInfos != null && flightsInfos.length > 1){
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
					segment.setDptTime(dptTime);
					segment.setArrTime(arrTime);
					segment.setAirLine(airLine);
					segment.setCarrier(carrier);
					segment.setDiscount(discount);
					segment.setCode(code);
					segment.setPlanType(planType);
					input.setSegments(segment);
				}
			}
		}		
		
		String passengersInfo = (String)m1.get("PASSENGER_INFO");
		if(passengersInfo.endsWith("|")) passengersInfo += " ";
		String[] passengersInfos = passengersInfo.split("\\^");		
		if(passengersInfos != null && passengersInfos.length > 1){
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
		
		
		
		
		createOrderRequest.setAccount("TY");
		createOrderRequest.setPwd("ty001");
		
		
		
		createOrderRequest.setInput(input);
		
		SearchOrderRequest searchOrderRequest = new SearchOrderRequest();
		searchOrderRequest.setAccount("TY");
		searchOrderRequest.setPwd("ty001");
		searchOrderRequest.setOrderId("131126143043870,131126143046796");
		
		try {
			System.out.println(createOrderRequest.getInputToXml());
			System.getProperties().setProperty("proxySet", "true"); // 如果不设置，只要代理IP和代理端口正确,此项不设置也可以
			System.getProperties().setProperty("http.proxyHost", "10.8.15.118");
			System.getProperties().setProperty("http.proxyPort", "606");
			System.out.println(l.createOrder(createOrderRequest));
			String arrTime="0803";
			System.out.println(arrTime.substring(0,2) + ":" + arrTime.substring(2,arrTime.length()));
			System.out.println("pwd=ty001&input=<Request><DptDate>2013-11-27</DptDate><TsrDate></TsrDate><BakDate>2013-11-28</BakDate><TripType>2</TripType><AdultNumber>1</AdultNumber><ChdNumber>1</ChdNumber><InfNumber></InfNumber><Issue>1</Issue><TkDate></TkDate><TkTime></TkTime><DeliveryType>2</DeliveryType><IsProvince></IsProvince><Route>1</Route><postPrice> </postPrice><Recipient>老王</Recipient><Post>441400</Post><RcptAddress>武汉</RcptAddress><SelfGetDate></SelfGetDate><SelfGetTime></SelfGetTime><SelfGetTime1></SelfGetTime1><DeliveryDate>2013-11-27</DeliveryDate><DeliveryTime>13:30</DeliveryTime><DeliveryTime1></DeliveryTime1><DeliveryAddress>武汉</DeliveryAddress><ContactName>老王</ContactName><ContactMobile>13312121212</ContactMobile><ContactTel>87553321</ContactTel><Email> </Email><Remark></Remark><DeliveryCity>武汉</DeliveryCity><InsurancePrice>20</InsurancePrice><PayType></PayType><Id></Id><MemberId></MemberId><Memberkh></Memberkh><Bank></Bank><Name></Name><CardNumber></CardNumber><Year></Year><Month></Month><IdeCode></IdeCode><Compid></Compid><Deptid></Deptid><Userid></Userid><Segments><Segment><Dpt>WUH</Dpt><Arr>PEK</Arr><DptTime>0805</DptTime><ArrTime>0950</ArrTime><AirLine>CZ3117</AirLine><Carrier>CZ</Carrier><Discount>0.80</Discount><Code>M</Code><PlanType>738</PlanType><Privilege></Privilege></Segment><Segment><Dpt>PEK</Dpt><Arr>WUH</Arr><DptTime>0835</DptTime><ArrTime>1045</ArrTime><AirLine>CZ6605</AirLine><Carrier>CZ</Carrier><Discount>0.75</Discount><Code>H</Code><PlanType>320</PlanType><Privilege></Privilege></Segment></Segments><Passengers><Passenger><Name>老王</Name><CertificateType>NI</CertificateType><CertificateNumber>420601197807230021</CertificateNumber><PassengerType>1</PassengerType><Csrq>1978-07-23</Csrq><InsuranceDeal>1</InsuranceDeal><Price>950</Price><AirTax>50</AirTax><Tax>120</Tax><Price1></Price1><AirTax1></AirTax1><Tax1></Tax1></Passenger><Passenger><Name>小王</Name><CertificateType>NI</CertificateType><CertificateNumber>420601200807230021</CertificateNumber><PassengerType>2</PassengerType><Csrq>2008-07-23</Csrq><InsuranceDeal>1</InsuranceDeal><Price>560</Price><AirTax>30</AirTax><Tax>60</Tax><Price1></Price1><AirTax1></AirTax1><Tax1></Tax1></Passenger></Passengers></Request>&account=TY".equals("pwd=ty001&input=<Request><DptDate>2013-11-27</DptDate><TsrDate></TsrDate><BakDate>2013-11-28</BakDate><TripType>2</TripType><AdultNumber>1</AdultNumber><ChdNumber>1</ChdNumber><InfNumber></InfNumber><Issue>1</Issue><TkDate></TkDate><TkTime></TkTime><DeliveryType>2</DeliveryType><IsProvince></IsProvince><Route>1</Route><postPrice> </postPrice><Recipient>老王</Recipient><Post>441400</Post><RcptAddress>武汉</RcptAddress><SelfGetDate></SelfGetDate><SelfGetTime></SelfGetTime><SelfGetTime1></SelfGetTime1><DeliveryDate>2013-11-27</DeliveryDate><DeliveryTime>13:30</DeliveryTime><DeliveryTime1></DeliveryTime1><DeliveryAddress>武汉</DeliveryAddress><ContactName>老王</ContactName><ContactMobile>13312121212</ContactMobile><ContactTel>87553321</ContactTel><Email> </Email><Remark></Remark><DeliveryCity>武汉</DeliveryCity><InsurancePrice>20</InsurancePrice><PayType></PayType><Id></Id><MemberId></MemberId><Memberkh></Memberkh><Bank></Bank><Name></Name><CardNumber></CardNumber><Year></Year><Month></Month><IdeCode></IdeCode><Compid></Compid><Deptid></Deptid><Userid></Userid><Segments><Segment><Dpt>WUH</Dpt><Arr>PEK</Arr><DptTime>0805</DptTime><ArrTime>0950</ArrTime><AirLine>CZ3117</AirLine><Carrier>CZ</Carrier><Discount>0.80</Discount><Code>M</Code><PlanType>738</PlanType><Privilege></Privilege></Segment><Segment><Dpt>PEK</Dpt><Arr>WUH</Arr><DptTime>0835</DptTime><ArrTime>1045</ArrTime><AirLine>CZ6605</AirLine><Carrier>CZ</Carrier><Discount>0.75</Discount><Code>H</Code><PlanType>320</PlanType><Privilege></Privilege></Segment></Segments><Passengers><Passenger><Name>老王</Name><CertificateType>NI</CertificateType><CertificateNumber>420601197807230021</CertificateNumber><PassengerType>1</PassengerType><Csrq>1978-07-23</Csrq><InsuranceDeal>1</InsuranceDeal><Price>950</Price><AirTax>50</AirTax><Tax>120</Tax><Price1></Price1><AirTax1></AirTax1><Tax1></Tax1></Passenger><Passenger><Name>小王</Name><CertificateType>NI</CertificateType><CertificateNumber>420601200807230021</CertificateNumber><PassengerType>2</PassengerType><Csrq>2008-07-23</Csrq><InsuranceDeal>1</InsuranceDeal><Price>560</Price><AirTax>30</AirTax><Tax>60</Tax><Price1></Price1><AirTax1></AirTax1><Tax1></Tax1></Passenger></Passengers></Request>&account=TY"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
