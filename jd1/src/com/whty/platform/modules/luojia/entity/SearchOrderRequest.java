package com.whty.platform.modules.luojia.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.whty.platform.common.config.Global;

public class SearchOrderRequest implements Serializable {
	private String account;
	private String pwd;
	private String orderId;
	
	public SearchOrderRequest(){}
	
	public SearchOrderRequest(String account,String pwd,String orderId){
		this.account = account;
		this.pwd = pwd;
		this.orderId = orderId;
	}	
	
		
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
