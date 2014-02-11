/**
 * Copyright &copy; 2012-2013 <a href="www.whty.com.cn">whty</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whty.platform.modules.luojia.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.whty.platform.common.persistence.BaseEntity;

/**
 * 字典Entity
 * 
 * @author 舒海洋
 * @version 2013-05-15
 */
@Entity
@Table(name = "tab_flight_order")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FlightOrder extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // 编号
	private Date dptDate;// 航班出发日期
	private Date arrDate;// 到达日期	
	private String userId;// 用户ID
	private Integer transType;// 交易类型
	private Integer tripType;// 航程类型1.单程，2.往返程，3联程
	private String flightInfo;// 航班信息
	private String contactName;// 联系人
	private String contactMobile;// 联系人手机
	private String contactTel;// 联系电话
	private String email;// 箱邮
	private String passengerInfo;//登机人信息
	private String expInfo;//快递收件信息
	private String delivery;//配送信息
	private String other;//
	private String remark;//
	private String orderId;//登机人信息
	private String customerOrderNo;//登机人信息
	private String pnrNo;//订座记录
	private String status;//订单状态

	public FlightOrder() {
		super();
	}

	public FlightOrder(Long id) {
		this();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDptDate() {
		return dptDate;
	}

	public void setDptDate(Date dptDate) {
		this.dptDate = dptDate;
	}

	public Date getArrDate() {
		return arrDate;
	}

	public void setArrDate(Date arrDate) {
		this.arrDate = arrDate;
	}	
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getTransType() {
		return transType;
	}

	public void setTransType(Integer transType) {
		this.transType = transType;
	}

	public Integer getTripType() {
		return tripType;
	}

	public void setTripType(Integer tripType) {
		this.tripType = tripType;
	}

	public String getFlightInfo() {
		return flightInfo;
	}

	public void setFlightInfo(String flightInfo) {
		this.flightInfo = flightInfo;
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

	public String getPassengerInfo() {
		return passengerInfo;
	}

	public void setPassengerInfo(String passengerInfo) {
		this.passengerInfo = passengerInfo;
	}

	public String getExpInfo() {
		return expInfo;
	}

	public void setExpInfo(String expInfo) {
		this.expInfo = expInfo;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerOrderNo() {
		return customerOrderNo;
	}

	public void setCustomerOrderNo(String customerOrderNo) {
		this.customerOrderNo = customerOrderNo;
	}

	public String getPnrNo() {
		return pnrNo;
	}

	public void setPnrNo(String pnrNo) {
		this.pnrNo = pnrNo;
	}

	public String getStatus() {
		if(status == null) return "";
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}