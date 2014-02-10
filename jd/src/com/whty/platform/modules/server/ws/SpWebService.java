package com.whty.platform.modules.server.ws;

import javax.jws.WebService;

@WebService(name = "spWebService",targetNamespace = "http://www.whty.com.cn")
public interface SpWebService {
	public String ws(String serviceName, String jsonParam);
}
