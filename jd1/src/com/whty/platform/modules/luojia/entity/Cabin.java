package com.whty.platform.modules.luojia.entity;

import java.io.Serializable;
import java.util.Map;

public class Cabin implements Serializable{
	private String code;
	private String status;
	private String type;
	private String price;
	private String zk;
	private String commisionPrice;
	private String commision;
	private String scgd;
	private String tpgd;
	private String bzbz;
	private String qzgd;
	private String yhj;
	private String codeName;
	private String zcid;
	private Map<String,String> attrbute;
	public Map<String, String> getAttrbute() {
		return attrbute;
	}
	public void setAttrbute(Map<String, String> attrbute) {
		this.attrbute = attrbute;
	}	
	
}
