package com.whty.platform.modules.kamen.entity;

public class Charge {

	private String CustomerOrderNo;// 客户外部系统订单号
	private String OrderNo;// 卡门网订单号
	private String Status;// 状态(Success)
	private String ErrorCode;// 错误码
	private String ErrorMsg;// 错误描述

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

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
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

}
