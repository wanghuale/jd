package com.whty.platform.modules.qindong.handler;

import java.util.ArrayList;

public class MessageField
{
  private String fieldName;
  private String fieldType;
  private String fieldValue;
  private ArrayList<MessageField> fieldList;

  public String getFieldName()
  {
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

  public ArrayList<MessageField> getFieldList() {
    return this.fieldList;
  }

  public void setFieldList(ArrayList<MessageField> fieldList) {
    this.fieldList = fieldList;
  }
}