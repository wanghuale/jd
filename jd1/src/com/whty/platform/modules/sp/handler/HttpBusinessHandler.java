package com.whty.platform.modules.sp.handler;

import com.whty.platform.modules.sp.entity.HttpBusinessRequest;
import com.whty.platform.modules.sp.entity.HttpBusinessResponse;


public abstract interface HttpBusinessHandler {
	public abstract HttpBusinessResponse handler(HttpBusinessRequest paramHttpBusinessRequest) throws Exception;
}