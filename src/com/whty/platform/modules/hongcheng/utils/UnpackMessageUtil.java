package com.whty.platform.modules.hongcheng.utils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnpackMessageUtil {
	private static final String secretKeyBef = "AEAFBFFC4EEDBAA6";
	private static final String secretKeyAft = "B22EEAF820C0EB97";

	private static Logger log = LoggerFactory
			.getLogger(UnpackMessageUtil.class);

	public static void main(String[] args) throws Exception {

		String paperPwd = "123123";
		String pwdByMd5 = getPaperPwdByMd5(paperPwd);
		log.debug("" + pwdByMd5);
	}

	/**
	 * 自定义报文解包
	 * 
	 * @param param
	 * @param listParam
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static List<ArrayList<String>> unPack(String[] param,
			String[] listParam, String message) throws Exception {
		ArrayList<ArrayList<String>> returnList = new ArrayList<ArrayList<String>>();
		int index = 0;
		ArrayList<String> normalList = new ArrayList<String>();
		for (int i = 0; i < param.length; i++) {
			int len = Integer.parseInt(param[i].split("-")[0]);
			String type = param[i].split("-")[1];
			String data = message.substring(index, index + len * 2);
			index += len * 2;
			if (type.trim().toUpperCase().equals("BCD")) {
				normalList.add(data);
			} else if (type.trim().toUpperCase().equals("ASC")) {
				normalList.add(new String(StringUtil.hexToBytes(data), "GBK").trim());
			} else {
				throw new Exception("不可识别的数据类型 : " + type);
			}
		}

		returnList.add(normalList);
		if (listParam == null) {
			return returnList;
		}
		while (index != message.length()) {
			ArrayList<String> dataList = new ArrayList<String>();
			for (int i = 0; i < listParam.length; i++) {
				int len = Integer.parseInt(listParam[i].split("-")[0]);
				String type = listParam[i].split("-")[1];
				if (index + len * 2 > message.length()) {// 传入报文不完整
					break;
				}
				String data = message.substring(index, index + len * 2);
				index += len * 2;
				if (type.trim().toUpperCase().equals("BCD")) {
					dataList.add(data);
				} else if (type.trim().toUpperCase().equals("ASC")) {
					dataList.add(new String(StringUtil.hexToBytes(data), "GBK").trim());
				} else {
					throw new Exception("不可识别的数据类型 : " + type);
				}
			}
			returnList.add(dataList);
		}

		return returnList;
	}

	/**
	 * 自定义报文组包
	 * 
	 * @param param
	 * @param messageSts
	 * @return
	 * @throws Exception
	 */
	public static String pack(String[] param, String[] messageSts)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < param.length; i++) {
			int len = Integer.parseInt(param[i].split("-")[0]);
			String type = param[i].split("-")[1]; // 字符类型BCD、ASC
			String data = messageSts[i];
			if (type.trim().toUpperCase().equals("BCD")) {
				sb.append(StringUtil.padding(data, len * 2, 1, 1));
			} else {
				sb.append(StringUtil.byteToHex(StringUtil.padding(data, len, 2)
						.getBytes("GBK")));
			}
		}
		return sb.toString();

	}

	/**
	 * 清算中心组包
	 * 
	 * @param param
	 * @param messageSts
	 * @return
	 * @throws Exception
	 */
	public static String packClearCenter(String[] param, String[] messageSts)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < param.length; i++) {
			int len = Integer.parseInt(param[i].split("-")[0]);
			String type = param[i].split("-")[1]; // 字符类型N 、A
			String data = messageSts[i];
			if (type.trim().toUpperCase().equals("N")) {
				sb.append(StringUtil.padding(data, len, 1, 1));
			} else {
				sb.append(StringUtil.padding(data, len, 2));
			}
		}
		return sb.toString();
	}

	/**
	 * 清算中心解包
	 * 
	 * @param paramHead
	 * @param paramBody
	 * @param paramList
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static List<ArrayList<String>> unpackClearCenter(String[] paramHead,
			String[] paramBody, String[] paramList, String message)
			throws Exception {
		ArrayList<ArrayList<String>> returnList = new ArrayList<ArrayList<String>>();
		if (message == null || message.equals("")) {
			return null;
		}
		int len = message.length();
		ArrayList<String> returnSts = new ArrayList<String>();
		int count = 0;
		for (int i = 0; i < paramHead.length; i++) {
			int lenHeadParam = Integer.parseInt(paramHead[i].split("-")[0]); // 长度
			message.substring(count, count + lenHeadParam * 2);
			count += lenHeadParam * 2;
			// returnSts.add(StringUtil.hexToAscii(code));
		}

		for (int j = 0; j < paramBody.length; j++) {
			int lenBodyParam = Integer.parseInt(paramBody[j].split("-")[0]); // 长度
			String code = message.substring(count, count + lenBodyParam * 2);
			count += lenBodyParam * 2;
			returnSts
					.add(new String(StringUtil.hexToBytes(code), "GBK").trim());
		}
		returnList.add(returnSts);
		if (paramList == null || count == len) {
			return returnList;
		} else {
			while (count != len) {
				ArrayList<String> dataArr = new ArrayList<String>();
				for (int m = 0; m < paramList.length; m++) {
					int lenListParam = Integer
							.parseInt(paramList[m].split("-")[0]);
					if (count + lenListParam * 2 > len) { // 传入报文不完整
						break;
					}
					String code = message.substring(count, count + lenListParam
							* 2);
					count += lenListParam * 2;
					dataArr.add(new String(StringUtil.hexToBytes(code), "GBK")
							.trim());
				}
				returnList.add(dataArr);
			}
			return returnList;
		}
	}

	/**
	 * 将城市卡数据更新到hashmap
	 * 
	 * @param hashMap
	 * @param paramSts
	 * @param paramNameSts
	 * @param keyList
	 * @throws Exception
	 */
	public static void UpdateHashBySts(HashMap<String, Object> hashMap,
			String[] paramSts, String[] paramNameSts, String[] keyList)
			throws Exception {
		for (int i = 0; i < paramNameSts.length; i++) {
			int index = 8 + i;
			if (paramNameSts[i].indexOf("-gbk") < 0) {
				hashMap.put(paramNameSts[i], paramSts[index]);
			} else {
				hashMap.put(paramNameSts[i].substring(0, paramNameSts[i]
						.indexOf("-gbk")), new String(paramSts[index]
						.getBytes(), "GBK").trim());
			}
		}
		if (keyList != null && paramNameSts.length + 8 < paramSts.length) {
			for (int j = 0; j < keyList.length; j++) {
				int index = 8 + j + paramNameSts.length;
				if (paramNameSts[j].indexOf("-gbk") < 0) {
					hashMap.put(keyList[j], paramSts[index]);
				} else {
					hashMap.put(keyList[j].substring(0, keyList[j]
							.indexOf("-gbk")), new String(paramSts[index]
							.getBytes(), "GBK").trim());
				}
			}
		}

	}

	/**
	 * 计算通讯押码
	 * 
	 * @param messageDateTime
	 * @param data
	 * @return
	 */
	public static String getMacII(String messageDateTime, String data) {
		String md5Text = secretKeyBef;
		md5Text += messageDateTime + data;
		md5Text += secretKeyAft;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(md5Text.getBytes("GBK"));
			byte b[] = md.digest();

			byte[] resultByte = new byte[4];
			System.arraycopy(b, 0, resultByte, 0, 4);

			for (int i = 1; i < 4; i++) {
				byte[] tempBts = new byte[4];
				System.arraycopy(b, i * 4, tempBts, 0, 4);
				resultByte[0] = (byte) (resultByte[0] ^ tempBts[0]);
				resultByte[1] = (byte) (resultByte[1] ^ tempBts[1]);
				resultByte[2] = (byte) (resultByte[2] ^ tempBts[2]);
				resultByte[3] = (byte) (resultByte[3] ^ tempBts[3]);
			}
			return StringUtil.byteToHex(resultByte);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		
		return null;
	}

	public static String getPaperPwdByMd5(String paperPwd) {
		String md5Text = StringUtil.padding(paperPwd, 20, 2, 2);

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(md5Text.getBytes("GBK"));
			byte b[] = md.digest();

			byte[] resultByte = new byte[4];
			System.arraycopy(b, 0, resultByte, 0, 4);

			for (int i = 1; i < 4; i++) {
				byte[] tempBts = new byte[4];
				System.arraycopy(b, i * 4, tempBts, 0, 4);
				resultByte[0] = (byte) (resultByte[0] ^ tempBts[0]);
				resultByte[1] = (byte) (resultByte[1] ^ tempBts[1]);
				resultByte[2] = (byte) (resultByte[2] ^ tempBts[2]);
				resultByte[3] = (byte) (resultByte[3] ^ tempBts[3]);
			}
			return StringUtil.byteToHex(resultByte);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		return null;
	}

	/**
	 * 计算美伽汇卡密码加密结果
	 * 
	 * @param pin
	 *            密码
	 * @param dataTwo
	 *            卡号
	 * @return
	 */
	public static String getSecPin(String pin, String dataTwo) {
		if (pin.length() > 12 || pin.length() < 6) {
			return null;
		}
		/**
		 * 密码补位 当密码长度小于10时 左补两位长度 后右补f到16位
		 */
		String pinHex = pin; // 密码
		if (pin.length() >= 10) {
			pinHex = pin.length() + pinHex;
		} else {
			pinHex = "0" + pin.length() + pinHex;
		}
		while (pinHex.length() < 16) {
			pinHex += "F";
		}

		byte[] pinByte = StringUtil.hexToBytes(pinHex); // 密码补位结果每两位构成一个byte
		// 获得一个长度为8的byte[]

		/**
		 * 卡号补位 去卡号的4-15位共12位 左补0000 到16位
		 */
		int dindex = dataTwo.length(); // 卡号

		dataTwo = dataTwo.substring(dindex - 13, dindex - 1);
		dataTwo = "0000" + dataTwo;

		byte[] panByte = StringUtil.hexToBytes(dataTwo); // 卡号补位结果每两位构成一个byte
		// 获得一个长度为8的byte[]

		/**
		 * 两个byte[]异或得到result
		 */
		byte[] result = new byte[8];
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) (pinByte[i] ^ panByte[i]);
		}
		// String temp = mackey.substring(0,16);
		String pinkey = "3131313131313131";
		// String pinkey = StringUtil.desEcb(decodekey, temp, "00", 1);
		// String checkKey = StringUtil.desEcb(pinkey,
		// "0000000000000000","00",0);
		// System.out.println("checkKey : "+checkKey);
		String passwordo = StringUtil.desEcb(pinkey, StringUtil
				.byteToHex(result), "00", 0);

		return passwordo;
	}
}
