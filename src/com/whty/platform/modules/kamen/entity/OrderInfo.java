package com.whty.platform.modules.kamen.entity;

public class OrderInfo {

	private String OrderId;
	private String CustomerOrderNo;
	private String GoodsId;
	private String GoodsName;
	private String ChargeNum;

	private String ChargeAccount;
	private String ChargePrice;
	private String ChargeTime;
	private String OrderStatus;

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getCustomerOrderNo() {
		return CustomerOrderNo;
	}

	public void setCustomerOrderNo(String customerOrderNo) {
		CustomerOrderNo = customerOrderNo;
	}

	public String getGoodsId() {
		return GoodsId;
	}

	public void setGoodsId(String goodsId) {
		GoodsId = goodsId;
	}

	public String getChargeNum() {
		return ChargeNum;
	}

	public void setChargeNum(String chargeNum) {
		ChargeNum = chargeNum;
	}

	public String getChargeAccount() {
		return ChargeAccount;
	}

	public void setChargeAccount(String chargeAccount) {
		ChargeAccount = chargeAccount;
	}

	public String getChargePrice() {
		return ChargePrice;
	}

	public void setChargePrice(String chargePrice) {
		ChargePrice = chargePrice;
	}

	public String getChargeTime() {
		return ChargeTime;
	}

	public void setChargeTime(String chargeTime) {
		ChargeTime = chargeTime;
	}

	public String getOrderStatus() {
		return OrderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}

	public String getGoodsName() {
		return GoodsName;
	}

	public void setGoodsName(String goodsName) {
		GoodsName = goodsName;
	}

}
