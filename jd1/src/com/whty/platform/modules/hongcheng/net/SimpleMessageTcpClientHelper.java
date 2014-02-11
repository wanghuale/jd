package com.whty.platform.modules.hongcheng.net;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.whty.platform.modules.hongcheng.utils.StringUtil;

/**
 * 
 * 无长度信息的报文发送，固定接收的缓冲区大小
 * 
 * 
 * @author Administrator
 * 
 */
@Service
public class SimpleMessageTcpClientHelper {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  // 支持接收最大1M的数据
  private int bufferSize = 1024 * 1024;
  private int bufferSize1= 1024;

  public String sendHexMessage(String host, int port, String message) throws Exception {

    logger.debug("send message to {}:{}", host, port);
    logger.debug("message:{}", message);

    Socket client = null;
    OutputStream os = null;
    InputStream is = null;

    try {
      client = new Socket(host, port);
      os = client.getOutputStream();
      os.write(StringUtil.hexToBytes(message));
      os.flush();

      is = client.getInputStream();
      int readLength = 0;
      byte[] b = new byte[bufferSize1];
      
      StringBuffer replyMessageSb = new StringBuffer(); 
      while((readLength=is.read(b)) != -1){						//循环读取，每次读1k的数据
    	  replyMessageSb.append(StringUtil.byteToHex(b, 0, readLength));
//    	  if(readLength < b.length){
//				break;
//    	  }
      }
      String replyMessage = replyMessageSb.toString();
//      readLength = is.read(b);
//
//      String replyMessage = StringUtil.byteToHex(b, 0, readLength);

      logger.debug("received message:{}", replyMessage);

      return replyMessage;

    } finally {
      clossAll(client, os, is);
    }
  }

  public String sendAsciiMessage(String host, int port, String message, String encoding) throws Exception {

    logger.debug("send message to {}:{}", host, port);
    logger.debug("message:{}", message);

    Socket client = null;
    OutputStream os = null;
    InputStream is = null;

    try {
      client = new Socket(host, port);
      os = client.getOutputStream();
      os.write(message.getBytes(encoding));
      os.flush();

      is = client.getInputStream();
      int readLength = 0;
      byte[] b = new byte[bufferSize1];
      StringBuffer replyMessageSb = new StringBuffer();
      while((readLength=is.read(b)) != -1){						//循环读取，每次读1k的数据
    	  replyMessageSb.append(StringUtil.byteToHex(b, 0, readLength));
      }
      String replyMessage = replyMessageSb.toString();
//      readLength = is.read(b);
//
//      String replyMessage = StringUtil.byteToHex(b, 0, readLength);
      logger.debug("received message:{}", replyMessage);
      return replyMessage;

    } finally {
      clossAll(client, os, is);
    }
  }

  private void clossAll(Socket client, OutputStream os, InputStream is) {
    if (os != null) {
      try {
        os.close();
      } catch (Exception e) {
        logger.error("Error", e);
      }
    }
    if (is != null) {
      try {
        is.close();
      } catch (Exception e) {
        logger.error("Error", e);
      }
    }
    if (client != null) {
      try {
        client.close();
      } catch (Exception e) {
        logger.error("Error", e);
      }
    }
  }

}
