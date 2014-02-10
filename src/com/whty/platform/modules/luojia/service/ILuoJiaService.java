package com.whty.platform.modules.luojia.service;

import java.util.Map;

public abstract interface ILuoJiaService {
	public abstract Map avh(String url,Map m) throws Exception;
	public abstract Map createOrder(String url,Map m) throws Exception;
	public abstract Map searchOrder(String url,Map m) throws Exception;
	public abstract Map checkOrder(String url,Map m) throws Exception; 
}
