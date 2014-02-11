package com.whty.platform.modules.qindong.handler;

import java.util.HashMap;

public abstract interface ServiceWrapper {
	public abstract HashMap<String, String> executeService(HashMap<String, String> paramHashMap);

}