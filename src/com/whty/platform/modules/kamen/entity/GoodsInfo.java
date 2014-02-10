package com.whty.platform.modules.kamen.entity;

public class GoodsInfo {

	private String GoodsID;// 商品编号
	private String GoodsName;// 商品名称
	private String GoodsParvalue;// 商品面值
	private String GoodsType;// 商品类型
	private String SellPrice;// 销售价
	private String PurchasePrice;// 进货价(包含密价)
	private String BanBuyTime;// 禁售时间
	private String TemplateGuid;// 模板GUID
	private String StockType;// 库存状态(0.库存不足 1.库存警报 2.库存充足)
	private String IsSell;// 是否上架(1.是 0.否)
	private String SelectNum;// 可选数量(只针对卡密有用)
	private String ErrorCode;// 错误码
	private String ErrorMsg;// 错误描述

	public String getGoodsID() {
		return GoodsID;
	}

	public void setGoodsID(String goodsID) {
		GoodsID = goodsID;
	}

	public String getGoodsName() {
		return GoodsName;
	}

	public void setGoodsName(String goodsName) {
		GoodsName = goodsName;
	}

	public String getGoodsParvalue() {
		return GoodsParvalue;
	}

	public void setGoodsParvalue(String goodsParvalue) {
		GoodsParvalue = goodsParvalue;
	}

	public String getGoodsType() {
		return GoodsType;
	}

	public void setGoodsType(String goodsType) {
		GoodsType = goodsType;
	}

	public String getSellPrice() {
		return SellPrice;
	}

	public void setSellPrice(String sellPrice) {
		SellPrice = sellPrice;
	}

	public String getPurchasePrice() {
		return PurchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		PurchasePrice = purchasePrice;
	}

	public String getBanBuyTime() {
		return BanBuyTime;
	}

	public void setBanBuyTime(String banBuyTime) {
		BanBuyTime = banBuyTime;
	}

	public String getTemplateGuid() {
		return TemplateGuid;
	}

	public void setTemplateGuid(String templateGuid) {
		TemplateGuid = templateGuid;
	}

	public String getStockType() {
		return StockType;
	}

	public void setStockType(String stockType) {
		StockType = stockType;
	}

	public String getIsSell() {
		return IsSell;
	}

	public void setIsSell(String isSell) {
		IsSell = isSell;
	}

	public String getSelectNum() {
		return SelectNum;
	}

	public void setSelectNum(String selectNum) {
		SelectNum = selectNum;
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
