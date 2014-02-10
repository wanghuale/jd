package com.whty.platform.modules.server.ws.impl;

import javax.jws.WebService;

import com.whty.platform.modules.server.ws.SpWebService;

@WebService(name = "spWebService", endpointInterface = "com.whty.platform.modules.server.ws.SpWebService", targetNamespace = "http://www.whty.com.cn")
public class SpWebServiceImpl implements SpWebService {

	public String ws(String serviceName, String jsonParam) {
		return "sp_say: " + serviceName + ", jsonParam is " + jsonParam;
	}
}
