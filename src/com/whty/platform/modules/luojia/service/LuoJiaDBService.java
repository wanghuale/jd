/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.luojia.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whty.platform.common.service.BaseService;
import com.whty.platform.modules.bussiness.dao.OrderRecordDao;
import com.whty.platform.modules.bussiness.entity.OrderRecord;
import com.whty.platform.modules.kamen.Exception.KamenException;
import com.whty.platform.modules.kamen.dao.GoodsInfoDao;
import com.whty.platform.modules.kamen.entity.Goods;
import com.whty.platform.modules.kamen.entity.GoodsCatalogInfo;
import com.whty.platform.modules.kamen.entity.GoodsInfo;
import com.whty.platform.modules.kamen.entity.KamenUser;
import com.whty.platform.modules.luojia.dao.FlightOrderDao;
import com.whty.platform.modules.luojia.entity.FlightOrder;
import com.whty.platform.modules.luojia.utils.DateUtils;
import com.whty.platform.modules.sp.entity.HttpBusinessRequest;
import com.whty.platform.modules.sp.entity.HttpBusinessResponse;
import com.whty.platform.modules.sp.utils.ActinoInfoUtils;

/**
 * 卡门接口
 * 
 * @author qimin
 * @version 2013-07-10
 */
@Service
@Transactional(readOnly = true)
public class LuoJiaDBService extends BaseService implements InitializingBean {
	
	
	@Autowired
	private FlightOrderDao flightOrderDao;
	
	@Autowired
	private OrderRecordDao orderRecordDao;
	
	@Transactional(readOnly = false)
	public void createFlightOrder(Map<String, Object> reqActionInfo,Map<String, Object> resActionInfo) throws Exception {
		String orderId = (String)resActionInfo.get("ORDERNO");
		String pnrNo = (String)resActionInfo.get("PNRNO");
		String userId = (String)reqActionInfo.get("USER_ID");
		String remark = (String)reqActionInfo.get("REMARK");
		String other = (String)reqActionInfo.get("OTHER");
		if(other.endsWith("|")) other += " ";
		String[] others = other.split("\\|");		
		String tripType = others[0];
		String dptDate = others[1];
		String arrDate = null;
		String transType = (String)reqActionInfo.get("TRANS_TYPE");
		String flightsInfo = (String)reqActionInfo.get("FLIGHT_INFO");
		if(flightsInfo.endsWith("|")) flightsInfo += " ";
		
		if(tripType.equals("1")){
			String[] flightInfos = flightsInfo.split("\\|");
			String dptTime = flightInfos[6];
			dptDate = dptDate + " " + dptTime;
			String arrTime = flightInfos[7];
			arrDate = dptDate + " " + arrTime;
		}else{
			String[] flightsInfos = flightsInfo.split("\\^");
			if(flightsInfos != null && flightsInfos.length > 0){
				if(flightsInfos[0].endsWith("|")) flightsInfos[0] += " ";
				String flightInfo[] = flightsInfos[0].split("\\|");
				String dptTime = flightInfo[6];
				String arrTime = flightInfo[7];
				dptDate = dptDate + " " + dptTime;
				arrDate = dptDate + " " + arrTime;
			}
		}
		String contactInfo = (String)reqActionInfo.get("CONTACT_INFO");
		if(contactInfo.endsWith("|")) contactInfo += " ";
		String[] contactInfos = contactInfo.split("\\|");
		String contactName = contactInfos[0];
		String contactMobile = contactInfos[1];
		String contactTel = contactInfos[2];
		String email = contactInfos[3];
		String passengerInfo = (String)reqActionInfo.get("PASSENGER_INFO");
		String expInfo = (String)reqActionInfo.get("EXP_INFO");
		String delivery = (String)reqActionInfo.get("DELIVERY");
		
		String customerOrderNo = (String)reqActionInfo.get("CUSTOMER_ORDER_NO");
		FlightOrder flightOrder = new FlightOrder();
		
		flightOrder.setDptDate(DateUtils.toDate(dptDate));
		flightOrder.setArrDate(DateUtils.toDate(arrDate));
		flightOrder.setTripType(new Integer(tripType));
		flightOrder.setUserId(userId);
		flightOrder.setTransType(new Integer(transType));
		flightOrder.setFlightInfo(flightsInfo);
		flightOrder.setContactName(contactName);
		flightOrder.setContactMobile(contactMobile);
		flightOrder.setContactTel(contactTel);
		flightOrder.setEmail(email);
		flightOrder.setPassengerInfo(passengerInfo);
		flightOrder.setExpInfo(expInfo);
		flightOrder.setDelivery(delivery);
		flightOrder.setOther(other);
		flightOrder.setRemark(remark);
		flightOrder.setOrderId(orderId);
		flightOrder.setCustomerOrderNo(customerOrderNo);
		flightOrder.setPnrNo(pnrNo);
		flightOrderDao.save(flightOrder);
	}
	
	public List<FlightOrder> findByDptDate() throws Exception{
		Date d = new Date();
		String now = DateUtils.getDateStringYMdHM(d);
		String addDate = DateUtils.addDateYMdHM(d, 10);
		return flightOrderDao.findBySql("select * from tab_flight_order where DATE_FORMAT(dpt_date,'%Y-%d-%m %H:%i') > '" + now + "' and DATE_FORMAT(dpt_date,'%Y-%d-%m') <= '" + addDate + "'", FlightOrder.class);
	}
	
	@Transactional(readOnly = false)
	public void updateOrderStatus(FlightOrder fOrder) throws Exception{
		flightOrderDao.save(fOrder);
	}
	
	@Transactional(readOnly = false)
	public void updateFlightOrderStatus(FlightOrder fOrder) throws Exception{
		
		if (StringUtils.isNotEmpty(fOrder.getCustomerOrderNo())) {
			String orderId = fOrder.getOrderId();
			if(orderId.contains(",")){
				String[] statuses = fOrder.getStatus().split(",");
				String[] orderIds = orderId.split(",");
				for(int i=0;i<orderIds.length;i++){
					DetachedCriteria dc = orderRecordDao.createDetachedCriteria();
					dc.add(Restrictions.eq("orderId", orderIds[i]));
					dc.add(Restrictions.eq("payOrderId", fOrder.getCustomerOrderNo()));
					dc.addOrder(Order.desc("createDate"));
					List<OrderRecord> list = orderRecordDao.find(dc);
					if (list.size() > 0) {
						OrderRecord orderRecord = list.get(0);
						orderRecord.setStatus(statuses[i]);
						orderRecordDao.save(orderRecord);
					}
				}
			}
		}
		
	}
	
	@Transactional(readOnly = false)
	public void createOrder(HttpBusinessRequest hbr,Map<String, Object> reqActionInfo,Map<String, Object> resActionInfo) throws Exception {
		
		String orderId = (String)resActionInfo.get("ORDERNO");
		String pnrNo = (String)resActionInfo.get("PNRNO");
		if(orderId.contains(",")){
			String[] orderIds = orderId.split(",");
			String[] pnrNos = pnrNo.split(",");
			for(int i=0;i<orderIds.length;i++){
				newOrder(hbr,reqActionInfo,orderIds[i],pnrNos[i]);
			}			
		}else{
			newOrder(hbr,reqActionInfo,orderId,pnrNo);
		}
	}
	
	private void newOrder(HttpBusinessRequest hbr,Map<String, Object> reqActionInfo,String orderId,String pnrNo){
		OrderRecord orderRecord = new OrderRecord();
		
		String customerOrderNo = (String)reqActionInfo.get("CUSTOMER_ORDER_NO");
		String flightsInfo = (String)reqActionInfo.get("FLIGHT_INFO");	
		String other = (String)reqActionInfo.get("OTHER");
		if(other.endsWith("|")) other += " ";
		String[] others = other.split("\\|");
		String tripType = others[0];
		String dptDate = others[1];
		String bakDate = others[3];
		String arrDate = null;
		String adultNumber = others[4];
		String chdNumber = others[5];			
		
		if(tripType.equals("1")){
			if(flightsInfo.endsWith("|")) flightsInfo += " ";
			String[] flightInfos = flightsInfo.split("\\|");
			String airLine = flightInfos[0];
			String dptName = flightInfos[3];
			String arrName = flightInfos[5];
			String dptTime = flightInfos[6];
			String dptDate1 = dptDate + " " + dptTime;
			String arrTime = flightInfos[7];
			arrDate = dptDate + " " + arrTime;
			orderRecord.setProductid(airLine);
			orderRecord.setProductname("行程:" + dptName + " 至 " + arrName);
			orderRecord.setRemarks("起飞时间:" + dptDate1 + " 到达时间:" + arrDate + ",订位记录:" + pnrNo);
		}else{
			String airLine = ""; 
			String dptArr = "";
			String dptTime = "";
			String arrTime = "";
			String[] flightsInfos = flightsInfo.split("\\^");
			if(flightsInfos != null && flightsInfos.length > 0){
				if(flightsInfos[0].endsWith("|")) flightsInfos[0] += " ";
				String airLine1 = flightsInfos[0].split("\\|")[0];
				String dTime1 = flightsInfos[0].split("\\|")[6];
				String aTime1 = flightsInfos[0].split("\\|")[7];
				String dptArr1 = "行程:" + flightsInfos[0].split("\\|")[3] + "至" + flightsInfos[0].split("\\|")[5];
				String dptDate2 = dptDate + " " + dTime1;
				String arrDate2 = dptDate + " " + aTime1;
				
				if(flightsInfos[1].endsWith("|")) flightsInfos[1] += " ";
				String airLine2 = flightsInfos[1].split("\\|")[0];
				String dTime2 = flightsInfos[1].split("\\|")[6];
				String aTime2 = flightsInfos[1].split("\\|")[7];
				String dptArr2 = " 返程:" + flightsInfos[1].split("\\|")[3] + "至" + flightsInfos[1].split("\\|")[5];
				String bakDate1 = bakDate + " " + dTime2;
				String bakArrDate = bakDate + " " + aTime2;
				orderRecord.setProductid(airLine1 + "," + airLine2);
				orderRecord.setProductname(dptArr1 + dptArr2);
				orderRecord.setRemarks("去程出发时间:" + dptDate2 + " 去程到达时间:" + arrDate2 + " 返程出发时间:" + bakDate1 + " 返程到达时间:" + bakArrDate + ",订位记录:" + pnrNo);
			}
		}
		
		String passengersInfo = (String)reqActionInfo.get("PASSENGER_INFO");
		String passenger = "";
		if(passengersInfo.endsWith("|")) passengersInfo += " ";
		String[] passengersInfos = passengersInfo.split("\\^");
		if(passengersInfos != null && passengersInfos.length > 0){
			for(String passengerInfos:passengersInfos){
				if(passengerInfos.endsWith("|")) passengerInfos += " ";
				String[] passengerInfo = passengerInfos.split("\\|");
				if(passenger.equals("")){
					passenger = passengerInfo[1];
				}else{
					passenger = passenger + "," + passengerInfo[1];
				}
			}
		}
		
		Date d = new Date();
		orderRecord.setDispaly(passenger);
		orderRecord.setUpdateDate(d);
		orderRecord.setPayOrderId(customerOrderNo);
		orderRecord.setCreateDate(d);
		orderRecord.setConsumer(hbr.getConsumer());
		orderRecord.setCallBackUrl(hbr.getConsumer().getNotifyurl());
		orderRecord.setService(hbr.getServices());
		orderRecord.setProvider(hbr.getServices().getProvider());
		orderRecord.setOrderId(orderId);
		orderRecord.setAmount(NumberUtils.toDouble((String)reqActionInfo.get("AMOUNT")));
		orderRecord.setGoodsparvalue(NumberUtils.toDouble((String)reqActionInfo.get("AMOUNT")));
		orderRecord.setGoodsprice(NumberUtils.toDouble((String)reqActionInfo.get("AMOUNT")));
		orderRecord.setPurchaserprice(NumberUtils.toDouble((String)reqActionInfo.get("AMOUNT")));
		orderRecord.setNums(Integer.parseInt(adultNumber) + Integer.parseInt(chdNumber));
		orderRecord.setStatus("1");		
		
		orderRecordDao.save(orderRecord);
	}


	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

}
