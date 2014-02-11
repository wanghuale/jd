package com.whty.platform.modules.sp.entity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.whty.platform.modules.qindong.handler.MessageField;
import com.whty.platform.modules.sp.utils.ActinoInfoUtils;

/**
 * 服务请求返回
 * 
 * @author qimin
 * 
 */
public class HttpBusinessResponse {

	private String actionReturnCode;
	private String actionModule;
	private String actionName;
	private String responseCode;
	private String replyMessage;

	private Double price;
	private String status;

	private List<MessageField> messageFieldList = Lists.newArrayList();

	private Map<String, Object> actionMap = new HashMap<String, Object>();

	public void addActionValue(String key, Object value) {
		actionMap.put(key, value);
	}

	public String getResponseCode() {
		return this.responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public List<MessageField> getMessageFieldList() {
		return this.messageFieldList;
	}

	public void setMessageFieldList(List<MessageField> messageFieldList) {
		this.messageFieldList = messageFieldList;
	}

	public String getReplyMessage() {
		try {
			replyMessage = ActinoInfoUtils.mapToJson(actionMap);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return replyMessage;
	}

	public void setReplyMessage(String replyMessage) {
		this.replyMessage = replyMessage;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActionReturnCode() {
		return actionReturnCode;
	}

	public void setActionReturnCode(String actionReturnCode) {
		this.actionReturnCode = actionReturnCode;
	}

	public String getActionModule() {
		return actionModule;
	}

	public void setActionModule(String actionModule) {
		this.actionModule = actionModule;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

}