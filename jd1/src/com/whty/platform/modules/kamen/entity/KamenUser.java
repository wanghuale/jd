package com.whty.platform.modules.kamen.entity;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.whty.platform.common.utils.security.MD5;

public class KamenUser {

	private String key = "";
	private String CustomerID = "";
	private String CustomerID_name = "CustomerID";

	private String CustomerPWD = "";
	private String url = "";
	private String ip = "";
	private String poit = "";
	private StringBuffer signValue = new StringBuffer();

	private boolean signmodel = false;

	public void setSignmodel(boolean signmodel) {
		this.signmodel = signmodel;
	}

	public boolean isSignmodel() {
		return signmodel;
	}

	Map<String, String> params = null;

	public KamenUser() {
		super();
		this.key = "BAA20338FD3E36050B62AD16226EA96D";
		this.CustomerID = "801479";
		this.CustomerPWD = "123456";
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}

	public String getCustomerPWD() {
		return CustomerPWD;
	}

	public void setCustomerPWD(String customerPWD) {
		CustomerPWD = customerPWD;
	}

	public String getSign() {
		MD5 md5 = new MD5();
		System.out.println(this.CustomerID_name + "=" + this.CustomerID + this.signValue.toString() + this.key);
		String sign = md5.getMD5ofStr(this.CustomerID_name + "=" + this.CustomerID + this.signValue.toString()
				+ this.key);
		return sign;
	}

	public Map<String, String> getParams() {
		initParams();
		params.put(this.CustomerID_name, this.CustomerID);
		params.put("Sign", this.getSign());
		return params;
	}

	public void addParam(String key, String value) {
		initParams();
		if (StringUtils.isNotBlank(value)) {
			if (signmodel) {
				signValue.append("&");
			}
			signValue.append(key).append("=").append(value);
			params.put(key, value);
		}
	}

	private void initParams() {
		if (params == null) {
			this.params = new HashMap<String, String>();
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPoit() {
		return poit;
	}

	public void setPoit(String poit) {
		this.poit = poit;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCustomerID_name() {
		return CustomerID_name;
	}

	public void setCustomerID_name(String customerID_name) {
		CustomerID_name = customerID_name;
	}

}
