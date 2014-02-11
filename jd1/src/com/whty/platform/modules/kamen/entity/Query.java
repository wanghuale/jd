package com.whty.platform.modules.kamen.entity;

public class Query {

	private String CustomerOrderNo;// 客户外部系统订单号
	private String OrderNo;// 卡门网订单号
	private String OrderStatus;// 订单状态(成功、失败、处理中、未处理、可疑)
	private String Description;// 充值描述
	private String ErrorCode;// 错误码
	private String ErrorMsg;// 错误描述
	private String ProductId;// 产品ID
	private String ProductName;// 产品名称

	public String getCustomerOrderNo() {
		return CustomerOrderNo;
	}

	public void setCustomerOrderNo(String customerOrderNo) {
		CustomerOrderNo = customerOrderNo;
	}

	public String getOrderNo() {
		return OrderNo;
	}

	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}

	public String getOrderStatus() {
		return OrderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}

	public String getErrorMsg() {
		return ErrorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}

	public String getProductId() {
		return ProductId;
	}

	public void setProductId(String productId) {
		ProductId = productId;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String productName) {
		ProductName = productName;
	}

}
