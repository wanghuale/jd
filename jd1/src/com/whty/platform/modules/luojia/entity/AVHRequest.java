package com.whty.platform.modules.luojia.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.whty.platform.common.config.Global;

public class AVHRequest implements Serializable {
	private String account;
	private String pwd;
	private String dpt;
	private String arr;
	private String date;
	private String arrLine;
	
	public AVHRequest(){}
	
	public AVHRequest(String account,String pwd,String dpt,String arr,String date,String arrLine){
		this.account = account;
		this.pwd = pwd;
		this.dpt = dpt;
		this.arr = arr;
		this.date = date;
		this.arrLine = arrLine;
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
	public String getDpt() {
		return dpt;
	}
	public void setDpt(String dpt) {
		this.dpt = dpt;
	}
	public String getArr() {
		return arr;
	}
	public void setArr(String arr) {
		this.arr = arr;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getArrLine() {
		return arrLine;
	}
	public void setArrLine(String arrLine) {
		this.arrLine = arrLine;
	}
	
	
}
