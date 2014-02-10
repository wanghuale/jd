/**
 * Copyright &copy; 2012-2013 <a href="www.whty.com.cn">whty</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whty.platform.modules.kamen.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.whty.platform.common.persistence.BaseEntity;

/**
 * 字典Entity
 * 
 * @author 舒海洋
 * @version 2013-05-15
 */
@Entity
@Table(name = "tab_goods")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Goods extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // 编号
	private String goodsId;// 商品编号
	private String goodsName;// 商品名称
	private String goodsParvalue;// 商品面值
	private String goodsType;// 商品类型
	private String sellPrice;// 销售价
	private String purchasePrice;// 进货价(包含密价)
	private String banBuyTime;// 禁售时间
	private String templateGuid;// 模板GUID
	private String stockType;// 库存状态(0.库存不足 1.库存警报 2.库存充足)
	private String isSell;// 是否上架(1.是 0.否)
	private String selectNum;// 可选数量(只针对卡密有用)
	private String goodsCatalogId;// 类目
	private String goodsCatalogName;
	
	private double spreadprice; //进售差价
	//private String p_goodsid;  //游戏大类id
	//private String p_goodsname; //游戏大类名称
	public Goods() {
		super();
	}

	public Goods(Long id) {
		this();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsParvalue() {
		return goodsParvalue;
	}

	public void setGoodsParvalue(String goodsParvalue) {
		this.goodsParvalue = goodsParvalue;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getBanBuyTime() {
		return banBuyTime;
	}

	public void setBanBuyTime(String banBuyTime) {
		this.banBuyTime = banBuyTime;
	}

	public String getTemplateGuid() {
		return templateGuid;
	}

	public void setTemplateGuid(String templateGuid) {
		this.templateGuid = templateGuid;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public String getIsSell() {
		return isSell;
	}

	public void setIsSell(String isSell) {
		this.isSell = isSell;
	}

	public String getSelectNum() {
		return selectNum;
	}

	public void setSelectNum(String selectNum) {
		this.selectNum = selectNum;
	}

	public String getGoodsCatalogId() {
		return goodsCatalogId;
	}

	public void setGoodsCatalogId(String goodsCatalogId) {
		this.goodsCatalogId = goodsCatalogId;
	}

	public String getGoodsCatalogName() {
		return goodsCatalogName;
	}

	public void setGoodsCatalogName(String goodsCatalogName) {
		this.goodsCatalogName = goodsCatalogName;
	}

	public double getSpreadprice() {
		return spreadprice;
	}

	public void setSpreadprice(double spreadprice) {
		this.spreadprice = spreadprice;
	}



}