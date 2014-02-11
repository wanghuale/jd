package com.whty.platform.modules.server.rmi;

public abstract interface RmiBusinessHandler {
	public abstract RmiBusinessResult executeBusiness(RmiBusinessRequest paramRmiBusinessRequest);
}