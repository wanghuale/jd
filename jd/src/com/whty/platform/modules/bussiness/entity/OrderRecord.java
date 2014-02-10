/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.whty.platform.common.persistence.BaseEntity;
import com.whty.platform.common.utils.excel.annotation.ExcelField;

/**
 * 订单Entity
 * 
 * @author qimin
 * @version 2013-08-13
 */
@Entity
@Table(name = "tab_order_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderRecord extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Long id; // 编号
	private String orderId;// // 供应商订单号
	private String payOrderId;// 顾客订单号

	private Consumer consumer;// '顾客IP',
	private String callBackUrl;// 顾客回调地址',
	private String remarks; // 备注
	private Double amount; // 支付金额

	private Double goodsprice; // 产品销售价格
	private Double goodsparvalue; // 商品面值
	private Double purchaserprice; // 产品进价

	protected Date createDate;// 交易时间

	private String dispaly; // 备注

	private String productid; // 产品编号
	private String productname; // 产品名称
	private Integer nums; // 数量
	private Date updateDate;// 处理时间
	private String status; // 状态
	private Provider provider;// 接入方ID provider_id int(15) 15 FALSE TRUE FALSE

	private Services service; // 供应商提供服务

	public OrderRecord() {
		super();
	}

	public OrderRecord(Long id) {
		this();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ExcelField(title = "本站订单号", align = 2, sort = 3)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@ExcelField(title = "外部订单号", align = 2, sort = 4)
	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@ExcelField(title = "交易时间", align = 2, sort = 9)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@ExcelField(title = "商品进价", align = 2, sort = 15)
	public Double getGoodsprice() {
		return goodsprice;
	}

	public void setGoodsprice(Double goodsprice) {
		this.goodsprice = goodsprice;
	}

	@ExcelField(title = "商品面值", align = 2, sort = 14)
	public Double getGoodsparvalue() {
		return goodsparvalue;
	}

	public void setGoodsparvalue(Double goodsparvalue) {
		this.goodsparvalue = goodsparvalue;
	}

	public Double getPurchaserprice() {
		return purchaserprice;
	}

	public void setPurchaserprice(Double purchaserprice) {
		this.purchaserprice = purchaserprice;
	}

	@ExcelField(title = "产品编号", align = 2, sort = 5)
	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	@ExcelField(title = "产品名称", align = 2, sort = 6)
	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	@ExcelField(title = "数量", align = 2, sort = 7)
	public Integer getNums() {
		return nums;
	}

	public void setNums(Integer nums) {
		this.nums = nums;
	}

	@ExcelField(title = "处理时间", align = 2, sort = 11, fieldType = Date.class)
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@ExcelField(title = "状态", align = 2, sort = 12, dictType = "order_status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provider")
	@NotFound(action = NotFoundAction.IGNORE)
	public Provider getProvider() {
		return provider;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "consumer")
	@NotFound(action = NotFoundAction.IGNORE)
	@ExcelField(title = "消费者名称", align = 2, sort = 3, value = "consumer.name")
	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public String getDispaly() {
		return dispaly;
	}

	public void setDispaly(String dispaly) {
		this.dispaly = dispaly;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "do_service")
	@NotFound(action = NotFoundAction.IGNORE)
	public Services getService() {
		return service;
	}

	public void setService(Services service) {
		this.service = service;
	}

}
