package com.whty.platform.modules.luojia.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Flight implements Serializable{
	private String dpt;
	private String arr;
	private String carrier;
	private String codeShare;
	private String code;
	private String dptTime;
	private String arrTime;
	private String eTicket;
	private String meal;
	private String plantype;
	private String stops;
	private String tax;
	private String airTax;
	private String yprice;
	private String price;
	private String lowCode;
	private String lowStatus;
	private String lowType;
	private String lowZk;
	private String lowCommisionPrice;
	private String lowCommision;
	private String lowscgd;
	private String lowtpgd;
	private String lowqzgd;
	private String lowbzbz;
	private String lowyhj;
	private String lowzcid;
	private String lowCodeName;
	private Map<String,String> attrbute;
	private List<Cabin> cabins = new ArrayList();	
	
	
	public Map<String, String> getAttrbute() {
		return attrbute;
	}
	public void setAttrbute(Map<String, String> attrbute) {
		this.attrbute = attrbute;
	}
	public List<Cabin> getCabins() {
		return cabins;
	}
	public void setCabins(List<Cabin> cabins) {
		this.cabins = cabins;
	}
	public void setCabins(Cabin cabin) {
		this.cabins.add(cabin);
	}
		
}
