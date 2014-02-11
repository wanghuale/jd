package com.whty.platform.modules.server.ws;

import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlType(name = "BusinessResult", namespace = "http://www.whty.com.cn")
public class WsBusinessResult {
	private String responseCode;
	private String replyMessage;

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getResponseCode() {
		return this.responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getReplyMessage() {
		return this.replyMessage;
	}

	public void setReplyMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}
}