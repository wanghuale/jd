package com.whty.platform.modules.sp.entity;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.whty.platform.modules.bussiness.entity.Consumer;
import com.whty.platform.modules.bussiness.entity.Services;
import com.whty.platform.modules.qindong.utils.HttpQDUtils;
import com.whty.platform.modules.server.utils.MapUtils;

public class HttpBusinessRequest {

	private Consumer consumer;
	private String actionName;
	private String actionUser;
	private String actionInfo;
	private String appId;
	private String message;
	private Map<String, Object> enMessageMap;
	private Map<String, Object> deMessageMap;
	private String remoteIp;
	private Services services;
	public static String notifyurl = "";

	public HttpBusinessRequest(String json) throws UnsupportedEncodingException {
		this.message = json;
		Map<String, Object> paramMap = HttpQDUtils.jsonToMap(json);
		this.actionUser = MapUtils.getValue(paramMap, "ACTION_USER");
		this.actionName = MapUtils.getValue(paramMap, "ACTION_NAME");
		this.actionInfo = MapUtils.getValue(paramMap, "ACTION_INFO");
		this.appId = MapUtils.getValue(paramMap, "APP_ID");
	}

	public String getRemoteIp() {
		return this.remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Map<String, Object> getEnMessageMap() {
		return enMessageMap;
	}

	public void setEnMessageMap(Map<String, Object> enMessageMap) {
		this.enMessageMap = enMessageMap;
	}

	public Map<String, Object> getDeMessageMap() {
		return deMessageMap;
	}

	public void setDeMessageMap(Map<String, Object> deMessageMap) {
		this.deMessageMap = deMessageMap;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getActionInfo() {
		return actionInfo;
	}

	public void setActionInfo(String actionInfo) {
		this.actionInfo = actionInfo;
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public String getKey() {
		return this.consumer.getkey();
	}

	public String getActionUser() {
		return actionUser;
	}

	public void setActionUser(String actionUser) {
		this.actionUser = actionUser;
	}

	public String getNotifyurl() {
		return HttpBusinessRequest.notifyurl;
	}

	public void setNotifyurl(String notifyurl) {
		HttpBusinessRequest.notifyurl = notifyurl;
	}

}