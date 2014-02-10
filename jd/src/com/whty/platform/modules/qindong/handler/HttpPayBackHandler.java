package com.whty.platform.modules.qindong.handler;

public abstract interface HttpPayBackHandler {
	public abstract HttpPayBackResponse handler(HttpPayBackRequest paramHttpBusinessRequest) throws Exception;
}