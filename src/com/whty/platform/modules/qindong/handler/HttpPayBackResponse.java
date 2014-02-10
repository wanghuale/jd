package com.whty.platform.modules.qindong.handler;

import org.springframework.beans.BeanUtils;

import com.whty.platform.common.utils.mapper.JsonMapper;

public class HttpPayBackResponse {

	private HttpPayBackResponseHead head;

	private HttpPayBackResponseBody body;

	public String tojson() {
		return JsonMapper.getInstance().toJson(this);
	}

	public String tojson(String method, String serialNumber, String version, String code, String message) {
		this.getHead().setMethod(method);
		this.getHead().setSerialNumber(serialNumber);
		this.getHead().setVersion(version);
		this.getBody().setCode(code);
		this.getBody().setMessage(message);
		return JsonMapper.getInstance().toJson(this);
	}

	private String datejson;//

	public void initByJson(String json) {
		this.datejson = json;
		HttpPayBackResponse s = JsonMapper.getInstance().fromJson(json, HttpPayBackResponse.class);
		BeanUtils.copyProperties(s, this);
	}

	public static void main(String[] args) {
		HttpPayBackResponse s = new HttpPayBackResponse();
		s.getHead().setMethod("123");
		s.getHead().setSerialNumber("123");
		s.getHead().setVersion("0");
		s.getBody().setCode("123");
		s.getBody().setMessage("you are good");
		System.out.println(s.tojson());
	}

	public HttpPayBackResponseHead getHead() {
		if (head == null) {
			head = new HttpPayBackResponseHead();
		}
		return head;
	}

	public void setHead(HttpPayBackResponseHead head) {
		this.head = head;
	}

	public HttpPayBackResponseBody getBody() {
		if (body == null) {
			body = new HttpPayBackResponseBody();
		}
		return body;
	}

	public void setBody(HttpPayBackResponseBody body) {
		this.body = body;
	}

	public String getDatejson() {
		return datejson;
	}

	public void setDatejson(String datejson) {
		this.datejson = datejson;
	}

}

class HttpPayBackResponseHead {
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

class HttpPayBackResponseBody {

	private String code;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
