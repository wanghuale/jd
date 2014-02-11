package com.whty.platform.modules.qindong.handler;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class BusinessResponse
{
  private String responseCode;
  private String messageFormat;
  private String message;

  public String getMessage()
  {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessageFormat() {
    return this.messageFormat;
  }

  public void setMessageFormat(String messageFormat) {
    this.messageFormat = messageFormat;
  }

  public String getResponseCode() {
    return this.responseCode;
  }

  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }

  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }
}