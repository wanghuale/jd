package com.whty.platform.modules.server.rmi;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RmiBusinessResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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