package com.whty.platform.modules.server.ws;

import javax.jws.WebService;

@WebService(name = "wsBusinessService", targetNamespace = "http://www.whty.com.cn")
public abstract interface WsBusinessHandler {
	public abstract WsBusinessResult executeBusiness(WsBusinessRequest paramWsBusinessRequest);
}