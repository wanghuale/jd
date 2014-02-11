package com.whty.platform.modules.qindong.handler;

import com.whty.platform.modules.bussiness.entity.Provider;
import com.whty.platform.modules.bussiness.entity.Services;

public class BusinessRequest {
	private String interfaceCode;
	private String interfaceVersion;
	private String interfaceAlgorithm;
	private String serviceCode;
	private String message;
	private String messageHead;
	private String messageBody;
	private String messageFormat;
	private String messageSource;
	private String remoteIp;
	private Provider provider;
	private Services services;

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

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageBody() {
		return this.messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getMessageFormat() {
		return this.messageFormat;
	}

	public void setMessageFormat(String messageFormat) {
		this.messageFormat = messageFormat;
	}

	public String getMessageSource() {
		return this.messageSource;
	}

	public void setMessageSource(String messageSource) {
		this.messageSource = messageSource;
	}

	public String getRemoteIp() {
		return this.remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public String getMessageHead() {
		return this.messageHead;
	}

	public void setMessageHead(String messageHead) {
		this.messageHead = messageHead;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}
	
	
	
}