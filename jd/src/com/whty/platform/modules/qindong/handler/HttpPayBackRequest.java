package com.whty.platform.modules.qindong.handler;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;

import com.whty.platform.common.utils.mapper.JsonMapper;

public class HttpPayBackRequest {
	private String mac;// mac Y String 200 为一套加密算法；保证数据传输过程中的安全性、完整性

	private HttpPayBackRequestHead head;

	private HttpPayBackRequestBody body;

	private String datejson;//

	public void initByJson(String json) {
		HttpPayBackRequest s = JsonMapper.getInstance().fromJson(json, HttpPayBackRequest.class);
		BeanUtils.copyProperties(s, this);
		this.datejson = json;
	}

	public static void main(String[] args) {
		String json = "";
		try {
			URL s = HttpPayBackRequest.class.getClassLoader().getResource("json.txt");
			System.out.println(s.getPath());
			json = FileUtils.readFileToString(new File(s.getPath()), "UTF-8");
			System.out.println(json);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		HttpPayBackRequest s = new HttpPayBackRequest();
		s.initByJson(json);
		System.out.println(s.getMac());
		System.out.println(s.getHead().getMethod());
		System.out.println(s.getBody().getOrderId());
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public HttpPayBackRequestHead getHead() {
		if (head == null) {
			head = new HttpPayBackRequestHead();
		}
		return head;
	}

	public void setHead(HttpPayBackRequestHead head) {
		this.head = head;
	}

	public HttpPayBackRequestBody getBody() {
		if (body == null) {
			body = new HttpPayBackRequestBody();
		}
		return body;
	}

	public void setBody(HttpPayBackRequestBody body) {
		this.body = body;
	}

	public String getDatejson() {
		return datejson;
	}

	public void setDatejson(String datejson) {
		this.datejson = datejson;
	}

}

class HttpPayBackRequestHead {
	private String method;// method Y String 6 024101
	private String serialNumber;// serialNumber N String 20 请求唯一流水号
	private String version;// version Y String 20 版本号；定值为1.0

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}

class HttpPayBackRequestBody {

	private String orderId;// orderId Y String 商圈订单号
	private String restTime;// restTime Y String
	private String amount;// amount Y String 总金额（单位分）
	private String mallId;// mallId Y String 支付平台分配给商圈的ID
	private String merchantId;// merchantId Y String 商户号
	private String merchantName;// merchantName Y String 商户名
	private String qid;// qid Y String 交易流水号 查询的交易在银联互联网系统中的流水号
	private String traceNumber;// traceNumber Y String 系统跟踪号 银联系统跟踪号
	private String traceTime;// traceTime Y String 系统跟踪时间 银联系统跟踪时间
	private String settleAmount;// settleAmount Y String 清算金额（单位分）
	private String settleCurrency;// settleCurrency Y String 清算币种
	private String settleDate;// settleDate Y String 清算日期
	private String exchangeRate;// exchangeRate Y String 清算汇率
	private String exchangeDate;// exchangeDate Y String 兑换日期
	private String status;// status Y String 2 状态
							// 0未支付；1支付失败；2支付成功；3交易关闭(未完成支付)；4撤销交易；5退货；6无此交易；

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRestTime() {
		return restTime;
	}

	public void setRestTime(String restTime) {
		this.restTime = restTime;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMallId() {
		return mallId;
	}

	public void setMallId(String mallId) {
		this.mallId = mallId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public String getTraceNumber() {
		return traceNumber;
	}

	public void setTraceNumber(String traceNumber) {
		this.traceNumber = traceNumber;
	}

	public String getTraceTime() {
		return traceTime;
	}

	public void setTraceTime(String traceTime) {
		this.traceTime = traceTime;
	}

	public String getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(String settleAmount) {
		this.settleAmount = settleAmount;
	}

	public String getSettleCurrency() {
		return settleCurrency;
	}

	public void setSettleCurrency(String settleCurrency) {
		this.settleCurrency = settleCurrency;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getExchangeDate() {
		return exchangeDate;
	}

	public void setExchangeDate(String exchangeDate) {
		this.exchangeDate = exchangeDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
