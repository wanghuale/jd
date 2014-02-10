package com.whty.platform.modules.server.ws;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "MessageField", namespace = "http://www.whty.com.cn")
public class WsMessageField {
	private String fieldName;
	private String fieldType;
	private String fieldValue;
	private ArrayList<WsMessageField> fieldList;

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return this.fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldValue() {
		return this.fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public ArrayList<WsMessageField> getFieldList() {
		return this.fieldList;
	}

	public void setFieldList(ArrayList<WsMessageField> fieldList) {
		this.fieldList = fieldList;
	}
}