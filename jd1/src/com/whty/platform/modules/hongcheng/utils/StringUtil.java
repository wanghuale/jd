package com.whty.platform.modules.hongcheng.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class StringUtil {

	public StringUtil() {

	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String msg = "39BA559821ABD004";
		System.out.println("============" + hexToAscii(msg));
	}

	public static String create8583Mac(String key, String message) {
		byte[] b = StringUtil.hexToBytes(message);
		byte[] b8 = new byte[b.length + (8 - b.length % 8)];
		System.arraycopy(b, 0, b8, 0, b.length);
		byte[] temp = new byte[8];
		for (int i = 0; i < b8.length; i++) {
			if (i < 8) {
				temp[i] = b8[i];
			} else {
				temp[i % 8] = (byte) (temp[i % 8] ^ b8[i]);
			}
		}
		String hex = StringUtil.byteToHex(temp);
		String desRes1 = desEcb(key,
				StringUtil.byteToHex(hex.getBytes(), 0, 8), "00", 0);
		byte[] desResByte = StringUtil.hexToBytes(desRes1);

		for (int i = 0; i < desResByte.length; i++) {
			desResByte[i] = (byte) (desResByte[i] ^ hex.getBytes()[i + 8]);
		}

		String desRes2 = desEcb(key, StringUtil.byteToHex(desResByte), "00", 0);
		return desRes2.substring(0, 8);
	}

	/**
	 * 0加密 1解密
	 * 
	 * @param key
	 * @param data
	 * @param padding
	 *            "80" "20" "00"
	 * @param mode
	 *            0加密 1解密
	 * @return
	 */
	public static String desEcb(String key, String srcData) {
		try {
			SecretKey keySpec = new SecretKeySpec(StringUtil.hexToBytes(key),
					"DES");// key
			Cipher enc = Cipher.getInstance("DES/ECB/NoPadding");
			enc.init(Cipher.ENCRYPT_MODE, keySpec);
			byte[] temp = enc.doFinal(StringUtil.hexToBytes(srcData));
			return StringUtil.byteToHex(temp, 0, 8);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 0加密 1解密
	 * 
	 * @param key
	 * @param data
	 * @param padding
	 *            "80" "20" "00"
	 * @param mode
	 *            0加密 1解密
	 * @return
	 */
	public static String desEcb(String key, String srcData, String padding,
			int mode) {
		try {
			if (key.length() % 2 != 0 || key.length() / 2 != 8) {
				return null;
			}
			if ((srcData.length() / 2) % 8 != 0) {
				srcData = srcData + padding;
			}

			while ((srcData.length() / 2) % 8 != 0) {
				srcData = srcData + "00";
			}
			SecretKey keySpec = new SecretKeySpec(StringUtil.hexToBytes(key),
					"DES");// key
			Cipher enc = Cipher.getInstance("DES/ECB/NoPadding");
			int cipherType = (mode == 0 ? Cipher.ENCRYPT_MODE
					: Cipher.DECRYPT_MODE);
			enc.init(cipherType, keySpec);
			byte[] temp = enc.doFinal(StringUtil.hexToBytes(srcData));
			return StringUtil.byteToHex(temp, 0, 8);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将16进制字符串转为2进制的bitMap
	 * 
	 * @param bitMap
	 * @return
	 */
	public static String dispackageBitMap(String bitMap) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < bitMap.length(); i++) {
			int x = Integer.parseInt(bitMap.charAt(i) + "", 16);
			buffer.append(StringUtil
					.padding(Integer.toBinaryString(x), 4, 1, 1));
		}
		return buffer.toString();
	}

	/**
	 * 补位
	 * 
	 * @param src
	 *            源
	 * @param length
	 *            补充到的长度
	 * @param paddingType
	 *            1:补0 2:补空格
	 * @param leftOrRight
	 *            1:左补 2:右补
	 * @return
	 */
	public static String padding(String src, int length, int paddingType,
			int leftOrRight) {
		String padddingStr = null;
		StringBuffer buffer = new StringBuffer(src);
		if (paddingType == 1) {
			padddingStr = "0";
		} else {
			padddingStr = " ";
		}

		while (buffer.length() < length) {
			if (leftOrRight == 1) {
				buffer.insert(0, padddingStr);
			} else {
				buffer.append(padddingStr);
			}
		}
		return buffer.toString();
	}

	/**
	 * 
	 * @param src
	 * @param length
	 * @param leftOrRight
	 * @return
	 */
	public static String padding(String src, int length, int leftOrRight) {
		try {
			byte[] returnByte = new byte[length];
			byte[] srcByte = src.getBytes("GBK");
			if (leftOrRight == 1) {
				System.arraycopy(srcByte, 0, returnByte, length
						- srcByte.length, srcByte.length);
				for (int i = 0; i < length - srcByte.length; i++) {
					returnByte[i] = 0x20;
				}
			} else {
				System.arraycopy(srcByte, 0, returnByte, 0, srcByte.length);
				for (int i = srcByte.length; i < length; i++) {
					returnByte[i] = 0x20;
				}
			}
			return new String(returnByte, "GBK");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	// public static String padding(String src, int length, int leftOrRight) {
	// StringBuffer srcHex = new StringBuffer();
	// try {
	// srcHex.append(StringUtil.byteToHex(src.getBytes("GBK")));
	// int len = length*2;
	// while(srcHex.length() < len){
	// if(leftOrRight == 1){
	//
	// }elseP{
	// srcHex.append("20");
	// }
	// }
	// } catch (UnsupportedEncodingException e) {
	// }
	//
	// return srcHex.toString();
	// }

	public static String removeZero(String src) {
		int j = 0;
		for (int i = 0; i < src.length(); i++) {
			if (src.charAt(i) == '0') {
				j++;
			} else {
				break;
			}
		}
		return src.substring(j);
	}

	/**
	 * 个人标识码(PIN)加密
	 * 
	 * @param pin
	 *            6-12个数 不在此范围内返回空
	 * @param dataTwo
	 *            2磁道数据
	 * @return 个人标识码
	 */
	public static String getSecPin(String pin, String dataTwo, String key) {
		if (pin.length() > 12 || pin.length() < 6) {
			return null;
		}

		String pinHex = pin;
		if (pin.length() >= 10) {
			pinHex = pin.length() + pinHex;
		} else {
			pinHex = "0" + pin.length() + pinHex;
		}
		while (pinHex.length() < 16) {
			pinHex += "F";
		}
		byte[] pinByte = StringUtil.hexToBytes(pinHex);

		int dindex = dataTwo.toLowerCase().indexOf("d");
		if (dindex < 0) {
			dindex = dataTwo.length();
		} else {
			dindex = dataTwo.toLowerCase().indexOf("d");
		}

		dataTwo = dataTwo.substring(dindex - 13, dindex - 1);
		dataTwo = "0000" + dataTwo;
		byte[] panByte = StringUtil.hexToBytes(dataTwo);

		byte[] result = new byte[8];
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) (pinByte[i] ^ panByte[i]);
		}

		String temp = key.substring(0, 16);
		String pinkey = StringUtil.desEcb("1111111111111111", temp, "00", 1);
		// String checkKey = StringUtil.desEcb(pinkey, "0000000000000000", "00",
		// 0);
		// System.out.println("checkKey : " + checkKey + "     9-12字节" +
		// key.substring(16, 24));
		String passwordo = StringUtil.desEcb(pinkey, StringUtil
				.byteToHex(result), "00", 0);
		return passwordo;
	}

	public static String byteBufferToString(java.nio.ByteBuffer buffer) {
		Charset charset = null;
		CharsetDecoder decoder = null;
		CharBuffer charBuffer = null;
		try {
			charset = Charset.forName("UTF-8");
			decoder = charset.newDecoder();
			charBuffer = decoder.decode(buffer);
			return charBuffer.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	public static String byteBufferToString(java.nio.ByteBuffer[] buffer) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < buffer.length; i++) {
			sb.append(byteBufferToString(buffer[i]));
		}
		return sb.toString();
	}

	/**
	 * 返回512长度之内的HEX字符串除以2之后的长度，用HEX字符串表示
	 * 例如传递的字符串长度为280，其16进制长度为140（0x8C）,用字符串8c表示
	 * 
	 * @param strValue
	 * @return
	 */
	public static String getStringHexLenWithinByte(String strValue) {
		int len = strValue.length() / 2;
		String result = Integer.toHexString(len);
		if (result.length() == 1) {
			result = "0" + result;
		}

		return result;
	}

	public static String trimStringBlank(String strValue) {
		String result = strValue.trim(); // 去掉左右的空格
		String space = " ";
		String tabCh = Character.toString((char) 9);

		// 去除中间的空格
		result = result.replaceAll(space, "");

		result = result.replaceAll(tabCh, "");

		return result;
	}

	public static int getHexDataLen(String data) {
		String strTemp = data.replaceAll(" ", "");

		return strTemp.length();
	}

	public static String formatHexDataWithSpace(String data) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < data.length(); i++) {
			if (i > 0 && (i % 2 == 0)) {
				buffer.append(" ");
			}
			buffer.append(data.charAt(i));
		}

		return buffer.toString();
	}

	public static String asciiToHex(String asciiString) {
		StringBuffer hexString = new StringBuffer();
		String hex;
		int iValue;
		byte[] buff = asciiString.getBytes();

		for (int i = 0; i < buff.length; i++) {
			iValue = buff[i];
			if (iValue < 0)
				iValue += 256;

			hex = Integer.toString(iValue, 16);
			if (hex.length() == 1)
				hexString.append("0" + hex);
			else
				hexString.append(hex);
		}

		return hexString.toString().toUpperCase();
	}

	/**
	 * 以指定的字符编码解析字符串的长度
	 * 
	 * @param txt
	 *            要解析的字符串
	 * @param charset
	 *            编码
	 * @return 字符串的长度
	 */
	public static int getStrLength(String txt, String charset) {
		try {
			return txt.getBytes(charset).length;
		} catch (UnsupportedEncodingException ex) {
			return txt.length();
		}
	}

	public static String byteToHex(byte[] buffer, int offset, int len) {
		StringBuffer hexString = new StringBuffer();
		String hex;
		int iValue;

		for (int i = offset; i < offset + len; i++) {
			iValue = buffer[i];
			if (iValue < 0)
				iValue += 256;

			hex = Integer.toString(iValue, 16);
			if (hex.length() == 1)
				hexString.append("0" + hex);
			else
				hexString.append(hex);
		}

		return hexString.toString().toUpperCase();
	}

	public static String byteToHex(byte[] buffer) {
		StringBuffer hexString = new StringBuffer();
		String hex;
		int iValue;

		for (int i = 0; i < buffer.length; i++) {
			iValue = buffer[i];
			if (iValue < 0)
				iValue += 256;

			hex = Integer.toString(iValue, 16);
			if (hex.length() == 1)
				hexString.append("0" + hex);
			else
				hexString.append(hex);
		}

		return hexString.toString().toUpperCase();
	}

	public static String asciiToHex(byte[] hexBuffer, int iOffset, int iLen) {
		StringBuffer hexString = new StringBuffer();
		String hex;
		int byteValue;

		for (int i = iOffset; i < iOffset + iLen; i++) {
			byteValue = hexBuffer[i];
			if (byteValue < 0) {
				byteValue += 256;
			}

			hex = Integer.toString(byteValue, 16);
			if (hex.length() == 1)
				hexString.append("0" + hex);
			else
				hexString.append(hex);
		}

		return hexString.toString().toUpperCase();
	}

	public static String intToHex(int value) {
		String hex;

		hex = Integer.toString(value, 16);

		if (hex.length() % 2 != 0) {
			hex = "0" + hex;
		}

		return hex.toUpperCase();
	}

	public static byte[] intToBytes(int value, int byteSize) {
		String hex = StringUtil.Int2HexStr(value, byteSize * 2);
		return StringUtil.hexToBytes(hex);
	}

	/**
	 * @param hex
	 *            将16进制的ascii 转成中文
	 * @return
	 */
	public static String hexToAscii(String hex) {
		byte[] buffer = new byte[hex.length() / 2];
		String strByte;

		for (int i = 0; i < buffer.length; i++) {
			strByte = hex.substring(i * 2, i * 2 + 2);
			buffer[i] = (byte) Integer.parseInt(strByte, 16);
		}

		return new String(buffer);
	}

	/**
	 * @param hex
	 *            每两个字节进行处理
	 * @return
	 */
	public static byte[] hexToBytes(String hex) {
		byte[] buffer = new byte[hex.length() / 2];
		String strByte;

		for (int i = 0; i < buffer.length; i++) {
			strByte = hex.substring(i * 2, i * 2 + 2);
			buffer[i] = (byte) Integer.parseInt(strByte, 16);
		}

		return buffer;
	}

	public static boolean hasChineseChar(String strValue) {
		boolean bResult = false;

		byte[] temp = strValue.getBytes();

		for (int i = 0; i < strValue.length(); i++) {
			if (temp[i] < 0) {
				bResult = true;
				break;
			}
		}

		return bResult;
	}

	public static boolean isNull(String value) {
		boolean bIsNull = false;

		if (value == null) {
			bIsNull = true;
			return bIsNull;
		}

		if (value.trim().length() == 0) {
			bIsNull = true;
		}

		return bIsNull;
	}

	public static String formatWithSpace(String value) {
		String formatedValue = value;
		String retValue;

		// 去掉所有的空格
		formatedValue = trimStringBlank(formatedValue);

		if (formatedValue.length() % 2 != 0) {
			formatedValue = formatedValue + "0";
		}

		retValue = "";
		for (int i = 0; i < formatedValue.length(); i += 2) {
			retValue += formatedValue.substring(i, i + 2) + " ";
		}

		return retValue;
	}

	public static String formatDateToString(Date date) {
		if (date == null)
			return "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

		return sdf.format(date);
	}

	public static String formatDateToString(Date date, String format) {
		if (date == null)
			return "";

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		return sdf.format(date);
	}

	/**
	 * 将一个byte数组转换为字符串
	 * 
	 * @param arr
	 *            要转换的byte数组
	 * @return 转换好的字符串，如果数组的length=0，则返回""。
	 */
	public static String bytetoString(byte[] arr) {
		String str = "";
		String tempStr = "";
		for (int i = 1; i < arr.length; i++) {
			tempStr = (Integer.toHexString(arr[i] & 0xff));
			if (tempStr.length() == 1) {
				str = str + "0" + tempStr;
			} else {
				str = str + tempStr;
			}
		}
		return str;
	}

	public static boolean isChineaseLetter(String name) {
		Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]*$");
		Matcher isNum = pattern.matcher(name);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 * 
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public static int length(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	public static String formatDateToEngString(Date date) {
		if (date == null)
			return "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		return sdf.format(date);
	}

	public static String splitAndFilterString(String input, int length) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		int len = str.length();
		if (len <= length) {
			return str;
		} else {
			str = str.substring(0, length);
			str += "......";
		}
		return str;
	}

	/**
	 * 将字符串格式化为指定的长度整数倍，不足填充对齐
	 * 
	 * @param data
	 * @param len
	 * @param fill
	 * @return
	 */
	public static String formatStringByLen(String data, int len, String fill) {
		String strData = trimStringBlank(data);

		int iFilledLen = strData.length() % len;
		if (iFilledLen > 0) {
			for (int i = 0; i < 8 - iFilledLen; i++) {
				strData += fill;
			}
		}

		return strData;
	}

	/**
	 * BCD字符串转换成byte数组
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] bcdToBytes(String data) {
		String strData = trimStringBlank(data).toUpperCase();
		int value;
		int j = 0;
		byte[] bData = new byte[strData.length() / 2];
		// 170957382130000F
		for (int i = 0; i < strData.length(); i++) {
			value = Character.digit(strData.charAt(i), 16);

			bData[j] = (byte) ((value << 4) & 0xF0);
			value = Character.digit(strData.charAt(i + 1), 16);
			bData[j] |= (byte) (value & 0x0F);

			i++;
			j++;
		}

		return bData;
	}

	public static String getSeperatedPathFromPackage(String rootPackage) {

		String path = rootPackage.replaceAll("\\.", "\\" + File.separator);

		if (!path.endsWith(File.separator)) {
			path += File.separator;
		}

		return path;
	}

	/**
	 * @param data
	 *            byte 0x 01
	 * @return 01
	 */
	public static String byteToHexStr(byte data) {

		String result = Integer.toString((data & 0xff) + 0x100, 16)
				.substring(1);
		return result;
	}

	/**
	 * 将整数转为16进行数后并以指定长度返回（当实际长度大于指定长度时只返回从末位开始指定长度的值）
	 * 
	 * @param val
	 *            int 待转换整数
	 * @param len
	 *            int 指定长度
	 * @return String
	 */
	public static String Int2HexStr(int val, int len) {
		String result = Integer.toHexString(val).toUpperCase();
		int r_len = result.length();
		if (r_len > len) {
			return result.substring(r_len - len, r_len);
		}
		if (r_len == len) {
			return result;
		}
		StringBuffer strBuff = new StringBuffer(result);
		for (int i = 0; i < len - r_len; i++) {
			strBuff.insert(0, '0');
		}
		return strBuff.toString();
	}

	public static String ucs2Decode(byte[] btsrc) {
		if (btsrc == null || btsrc.length == 0) {
			return "";
		}
		String retstr = "";
		int nLen = btsrc.length;
		byte[] bt;
		try {
			if (btsrc[0] == (byte) 0x80) {
				bt = new byte[nLen - 1];
				System.arraycopy(btsrc, 1, bt, 0, nLen - 1);
				retstr = (new String(bt, "UTF-16BE"));
			} else {
				bt = new byte[nLen];
				System.arraycopy(btsrc, 0, bt, 0, nLen);
				retstr = (new String(bt, "ISO-8859-1"));
			}
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		return retstr;
	}

	public static void BytesCopy(byte[] dest, byte[] source, int offset1,
			int offset2, int len) {
		for (int i = 0; i < len; i++) {
			dest[offset1 + i] = source[offset2 + i];
		}
	}

}
