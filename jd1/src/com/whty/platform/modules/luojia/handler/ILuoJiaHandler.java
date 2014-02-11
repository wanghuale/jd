package com.whty.platform.modules.luojia.handler;

import java.util.Map;

import com.whty.platform.modules.luojia.entity.AVHRequest;
import com.whty.platform.modules.luojia.entity.CreateOrderRequest;
import com.whty.platform.modules.luojia.entity.SearchOrderRequest;

public abstract interface ILuoJiaHandler {
	public abstract Map avh(AVHRequest request);
	public abstract Map createOrder(CreateOrderRequest request);
	public abstract Map searchOrder (SearchOrderRequest request);
	public abstract Map checkOrder (String orderId);
}
