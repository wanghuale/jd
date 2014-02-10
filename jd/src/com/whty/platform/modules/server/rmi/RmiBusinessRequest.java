package com.whty.platform.modules.server.rmi;

import java.io.Serializable;

public class RmiBusinessRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String interfaceCode;
	private String interfaceVersion;
	private String interfaceAlgorithm;
	private String serviceCode;
	private String remoteIp;
	private String message;
	private String messageFormat;

	public String getRemoteIp() {
		return this.remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageFormat() {
		return this.messageFormat;
	}

	public void setMessageFormat(String messageFormat) {
		this.messageFormat = messageFormat;
	}

	public String getInterfaceCode() {
		return this.interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public String getInterfaceVersion() {
		return this.interfaceVersion;
	}

	public void setInterfaceVersion(String interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}

	public String getInterfaceAlgorithm() {
		return this.interfaceAlgorithm;
	}

	public void setInterfaceAlgorithm(String interfaceAlgorithm) {
		this.interfaceAlgorithm = interfaceAlgorithm;
	}

	public String getServiceCode() {
		return this.serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
}