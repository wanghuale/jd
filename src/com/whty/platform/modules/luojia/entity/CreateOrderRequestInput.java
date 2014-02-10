package com.whty.platform.modules.luojia.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateOrderRequestInput {
	private String dptDate;
	private String tsrDate;
	private String bakDate;
	private String tripType;
	private String adultNumber;
	private String chdNumber;
	private String infNumber;
	private String issue = "1";
	private String tkDate;
	private String tkTime;
	private String deliveryType;
	private String isProvince;
	private String route;
	private String postPrice;
	private String recipient;
	private String post;
	private String rcptAddress;
	private String selfGetDate;
	private String selfGetTime;
	private String selfGetTime1;
	private String deliveryDate;
	private String deliveryTime;
	private String deliveryTime1;
	private String deliveryAddress;
	private String contactName;
	private String contactMobile;
	private String contactTel;
	private String email;
	private String remark;
	private String deliveryCity;
	private String insurancePrice;
	private String payType;
	private String id;
	private String memberId;
	private String memberkh;
	private String bank;
	private String name;
	private String cardNumber;
	private String year;
	private String month;
	private String ideCode;
	private String compid;
	private String deptid;
	private String userid;
	
	private List<CreateOrderRequestPassenger> passengers = new ArrayList();
	private List<CreateOrderRequestSegment> segments = new ArrayList();

	public String getDptDate() {
		return dptDate;
	}

	public void setDptDate(String dptDate) {
		this.dptDate = dptDate;
	}

	public String getTsrDate() {
		return tsrDate;
	}

	public void setTsrDate(String tsrDate) {
		this.tsrDate = tsrDate;
	}

	public String getBakDate() {
		return bakDate;
	}

	public void setBakDate(String bakDate) {
		this.bakDate = bakDate;
	}

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public String getAdultNumber() {
		return adultNumber;
	}

	public void setAdultNumber(String adultNumber) {
		this.adultNumber = adultNumber;
	}

	public String getChdNumber() {
		return chdNumber;
	}

	public void setChdNumber(String chdNumber) {
		this.chdNumber = chdNumber;
	}

	public String getInfNumber() {
		return infNumber;
	}

	public void setInfNumber(String infNumber) {
		this.infNumber = infNumber;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getTkDate() {
		return tkDate;
	}

	public void setTkDate(String tkDate) {
		this.tkDate = tkDate;
	}

	public String getTkTime() {
		return tkTime;
	}

	public void setTkTime(String tkTime) {
		this.tkTime = tkTime;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getIsProvince() {
		return isProvince;
	}

	public void setIsProvince(String isProvince) {
		this.isProvince = isProvince;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getPostPrice() {
		return postPrice;
	}

	public void setPostPrice(String postPrice) {
		this.postPrice = postPrice;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getRcptAddress() {
		return rcptAddress;
	}

	public void setRcptAddress(String rcptAddress) {
		this.rcptAddress = rcptAddress;
	}

	public String getSelfGetDate() {
		return selfGetDate;
	}

	public void setSelfGetDate(String selfGetDate) {
		this.selfGetDate = selfGetDate;
	}

	public String getSelfGetTime() {
		return selfGetTime;
	}

	public void setSelfGetTime(String selfGetTime) {
		this.selfGetTime = selfGetTime;
	}

	public String getSelfGetTime1() {
		return selfGetTime1;
	}

	public void setSelfGetTime1(String selfGetTime1) {
		this.selfGetTime1 = selfGetTime1;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getDeliveryTime1() {
		return deliveryTime1;
	}

	public void setDeliveryTime1(String deliveryTime1) {
		this.deliveryTime1 = deliveryTime1;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeliveryCity() {
		return deliveryCity;
	}

	public void setDeliveryCity(String deliveryCity) {
		this.deliveryCity = deliveryCity;
	}

	public String getInsurancePrice() {
		return insurancePrice;
	}

	public void setInsurancePrice(String insurancePrice) {
		this.insurancePrice = insurancePrice;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberkh() {
		return memberkh;
	}

	public void setMemberkh(String memberkh) {
		this.memberkh = memberkh;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getIdeCode() {
		return ideCode;
	}

	public void setIdeCode(String ideCode) {
		this.ideCode = ideCode;
	}

	public String getCompid() {
		return compid;
	}

	public void setCompid(String compid) {
		this.compid = compid;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}


	public List getPassengers() {
		return passengers;
	}

	public void setPassengers(CreateOrderRequestPassenger passenger) {
		this.passengers.add(passenger);
	}	
	
	public List getSegments() {
		return segments;
	}

	public void setSegments(CreateOrderRequestSegment segment) {
		this.segments.add(segment);
	}	
	
}
