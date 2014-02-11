package com.whty.platform.modules.luojia.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whty.platform.common.config.Global;
import com.whty.platform.modules.luojia.utils.StringUtils;

public class CreateOrderRequest implements Serializable {
	private String account;
	private String pwd;
	private CreateOrderRequestInput input;

	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public CreateOrderRequestInput getInput() {
		return input;
	}
	public String getInputToXml() throws Exception {
		
		String xml = "<Request>" +
		"<DptDate>" + StringUtils.getValue(input.getDptDate()) + "</DptDate>" +
		"<TsrDate>" + StringUtils.getValue(input.getTsrDate()) + "</TsrDate>" +
		"<BakDate>" + StringUtils.getValue(input.getBakDate()) + "</BakDate>" +
		"<TripType>" + StringUtils.getValue(input.getTripType()) + "</TripType>" +
		"<AdultNumber>" + StringUtils.getValue(input.getAdultNumber()) + "</AdultNumber>" +
		"<ChdNumber>" + StringUtils.getValue(input.getChdNumber()) + "</ChdNumber>" +
		"<InfNumber>" + StringUtils.getValue(input.getInfNumber()) + "</InfNumber>" +
		"<Issue>" + StringUtils.getValue(input.getIssue()) + "</Issue>" +
		"<TkDate></TkDate>" +
		"<TkTime></TkTime>" +
		"<DeliveryType>" + StringUtils.getValue(input.getDeliveryType()) + "</DeliveryType>" +
		"<IsProvince>" + StringUtils.getValue(input.getIsProvince()) + "</IsProvince>" +
		"<Route>" + StringUtils.getValue(input.getRoute()) + "</Route>" +
		"<postPrice>" + StringUtils.getValue(input.getPostPrice()) + "</postPrice>" +
		"<Recipient>" + StringUtils.getUTF8Value(input.getRecipient()) + "</Recipient>" +
		"<Post>" + StringUtils.getValue(input.getPost()) + "</Post>" +
		"<RcptAddress>" + StringUtils.getUTF8Value(input.getRcptAddress()) + "</RcptAddress>" +
		"<SelfGetDate>" + StringUtils.getValue(input.getSelfGetDate()) + "</SelfGetDate>" +
		"<SelfGetTime>" + StringUtils.getValue(input.getSelfGetTime()) + "</SelfGetTime>" +
		"<SelfGetTime1>" + StringUtils.getValue(input.getSelfGetTime1()) + "</SelfGetTime1>" +
		"<DeliveryDate>" + StringUtils.getValue(input.getDeliveryDate()) + "</DeliveryDate>" +
		"<DeliveryTime>" + StringUtils.getValue(input.getDeliveryTime()) + "</DeliveryTime>" +
		"<DeliveryTime1>" + StringUtils.getValue(input.getDeliveryTime1()) + "</DeliveryTime1>" +
		"<DeliveryAddress>" + StringUtils.getUTF8Value(input.getDeliveryAddress()) + "</DeliveryAddress>" +
		"<ContactName>" + StringUtils.getUTF8Value(input.getContactName()) + "</ContactName>" +
		"<ContactMobile>" + StringUtils.getValue(input.getContactMobile()) + "</ContactMobile>" +
		"<ContactTel>" + StringUtils.getValue(input.getContactTel()) + "</ContactTel>" +
		"<Email>" + StringUtils.getValue(input.getEmail()) + "</Email>" +
		"<Remark>" + StringUtils.getUTF8Value(input.getRemark()) + "</Remark>" +
		"<DeliveryCity>" + StringUtils.getUTF8Value(input.getDeliveryCity()) + "</DeliveryCity>" +
		"<InsurancePrice>" + StringUtils.getValue(input.getInsurancePrice()) + "</InsurancePrice>" +
		"<PayType>" + StringUtils.getValue(input.getPayType()) + "</PayType>" +
		"<Id>" + StringUtils.getValue(input.getId()) + "</Id>" +
		"<MemberId>" + StringUtils.getValue(input.getMemberId()) + "</MemberId>" +
		"<Memberkh>" + StringUtils.getValue(input.getMemberkh()) + "</Memberkh>" +
		"<Bank>" + StringUtils.getValue(input.getBank()) + "</Bank>" +
		"<Name>" + StringUtils.getUTF8Value(input.getName()) + "</Name>" +
		"<CardNumber>" + StringUtils.getValue(input.getCardNumber()) + "</CardNumber>" +
		"<Year>" + StringUtils.getValue(input.getYear()) + "</Year>" +
		"<Month>" + StringUtils.getValue(input.getMonth()) + "</Month>" +
		"<IdeCode>" + StringUtils.getValue(input.getIdeCode()) + "</IdeCode>" +
		"<Compid>" + StringUtils.getValue(input.getCompid()) + "</Compid>" +
		"<Deptid>" + StringUtils.getValue(input.getDeptid()) + "</Deptid>" +
		"<Userid>" + StringUtils.getValue(input.getUserid()) + "</Userid>";
		
		List<CreateOrderRequestSegment> segments = input.getSegments();
		if(segments != null && segments.size() > 0){
			xml += "<Segments>";
			for(CreateOrderRequestSegment segment : segments){
				xml += "<Segment>";
				xml += "<Dpt>" + StringUtils.getValue(segment.getDpt()) + "</Dpt>";
				xml += "<Arr>" + StringUtils.getValue(segment.getArr()) + "</Arr>";
				xml += "<DptTime>" + StringUtils.getValue(segment.getDptTime()) + "</DptTime>";
				xml += "<ArrTime>" + StringUtils.getValue(segment.getArrTime()) + "</ArrTime>";
				xml += "<AirLine>" + StringUtils.getValue(segment.getAirLine()) + "</AirLine>";
				xml += "<Carrier>" + StringUtils.getValue(segment.getCarrier()) + "</Carrier>";
				xml += "<Discount>" + StringUtils.getValue(segment.getDiscount()) + "</Discount>";
				xml += "<Code>" + StringUtils.getValue(segment.getCode()) + "</Code>";
				xml += "<PlanType>" + StringUtils.getValue(segment.getPlanType()) + "</PlanType>";
				xml += "<Privilege>" + StringUtils.getValue(segment.getPrivilege()) + "</Privilege>";
				xml += "</Segment>";
			}
			xml += "</Segments>";		
		}
		
		List<CreateOrderRequestPassenger> passengers = input.getPassengers();
		if(passengers != null && passengers.size() > 0){
			xml += "<Passengers>";
			for(CreateOrderRequestPassenger passenger : passengers){
					xml += "<Passenger>";
						xml += "<Name>" + StringUtils.getUTF8Value(passenger.getName()) + "</Name>";
						xml += "<CertificateType>" + StringUtils.getValue(passenger.getCertificateType()) + "</CertificateType>";
						xml += "<CertificateNumber>" + StringUtils.getValue(passenger.getCertificateNumber()) + "</CertificateNumber>";
						xml += "<PassengerType>" + StringUtils.getValue(passenger.getPassengerType()) + "</PassengerType>";
						xml += "<Csrq>" + StringUtils.getValue(passenger.getCsrq()) + "</Csrq>";
						xml += "<InsuranceDeal>" + StringUtils.getValue(passenger.getInsuranceDeal()) + "</InsuranceDeal>";
						xml += "<Price>" + StringUtils.getValue(passenger.getPrice()) + "</Price>";
						xml += "<AirTax>" + StringUtils.getValue(passenger.getAirTax()) + "</AirTax>";
						xml += "<Tax>" + StringUtils.getValue(passenger.getTax()) + "</Tax>";
						xml += "<Price1>" + StringUtils.getValue(passenger.getPrice1()) + "</Price1>";
						xml += "<AirTax1>" + StringUtils.getValue(passenger.getAirTax1()) + "</AirTax1>";
						xml += "<Tax1>" + StringUtils.getValue(passenger.getTax1()) + "</Tax1>";
					xml += "</Passenger>";
			}
			xml += "</Passengers>";
		}
		xml += "</Request>";
		return xml;
	}
	public void setInput(CreateOrderRequestInput input) {
		this.input = input;
	}

}
