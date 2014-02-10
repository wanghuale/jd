package com.whty.platform.modules.kamen.entity;

import org.apache.commons.lang.StringUtils;

public class RequestCharge {
	private String CustomerId;// string 是 云接口用户ID
	private String CustomerOrderNo;// string 是 客户外部系统订单号
	private String ProductId;// string 是 要充值的商品编号
	private String BuyNum;// string 是 购买数量
	private String ChargeAccount;// string 是 要充值的游戏账号/手机号/电话/QQ号等
	private String ChargePassword;// string 否 要充值的账号的密码，部分游戏需要。
	private String ChargeGame;// string 否 要充值的游戏，部分游戏需要。
	private String ChargeRegion;// string 否 要充值的游戏的区的名称，部分游戏需要。
	private String ChargeServer;// string 否 要充值的游戏的服务器的名称，部分游戏需要。
	private String ChargeType;// string 否 要充值的游戏的计费方式/充值类型，部分游戏需要。
	private String NotifyUrl;// string 否 客户外部系统用户接收充值结果的地址。
	private String Sign;// string 是 签名字符串

	public String getCustomerId() {
		return CustomerId;
	}

	public void setCustomerId(String customerId) {
		CustomerId = customerId;
	}

	public String getCustomerOrderNo() {
		return CustomerOrderNo;
	}

	public void setCustomerOrderNo(String customerOrderNo) {
		CustomerOrderNo = customerOrderNo;
	}

	public String getProductId() {
		return ProductId;
	}

	public void setProductId(String productId) {
		ProductId = productId;
	}

	public String getBuyNum() {
		return BuyNum;
	}

	public void setBuyNum(String buyNum) {
		BuyNum = buyNum;
	}

	public String getChargeAccount() {
		return ChargeAccount;
	}

	public void setChargeAccount(String chargeAccount) {
		ChargeAccount = chargeAccount;
	}

	public String getChargePassword() {
		return ChargePassword;
	}

	public void setChargePassword(String chargePassword) {
		ChargePassword = chargePassword;
	}

	public String getChargeGame() {
		return ChargeGame;
	}

	public void setChargeGame(String chargeGame) {
		ChargeGame = chargeGame;
	}

	public String getChargeRegion() {
		return ChargeRegion;
	}

	public void setChargeRegion(String chargeRegion) {
		ChargeRegion = chargeRegion;
	}

	public String getChargeServer() {
		return ChargeServer;
	}

	public void setChargeServer(String chargeServer) {
		ChargeServer = chargeServer;
	}

	public String getChargeType() {
		return ChargeType;
	}

	public void setChargeType(String chargeType) {
		ChargeType = chargeType;
	}

	public String getNotifyUrl() {
		if (StringUtils.isBlank(NotifyUrl)) {
			return "http";
		}
		return NotifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		NotifyUrl = notifyUrl;
	}

	public String getSign() {
		return Sign;
	}

	public void setSign(String sign) {
		Sign = sign;
	}

}
