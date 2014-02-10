package com.whty.platform.common.utils.security;
/**
 * Copyright (c) 2005-2012 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.whty.platform.common.utils.Exceptions;

/**
 * 支持HMAC-SHA1消息签名 及 DES/AES对称加密的工具类.
 * 
 * 支持Hex与Base64两种编码方式.
 * 
 * @author calvin
 */
public class Cryptos {

	private static final String AES = "AES";
	private static final String AES_CBC = "AES/CBC/PKCS5Padding";
	private static final String HMACSHA1 = "HmacSHA1";

	private static final int DEFAULT_HMACSHA1_KEYSIZE = 160; // RFC2401
	private static final int DEFAULT_AES_KEYSIZE = 128;
	private static final int DEFAULT_IVSIZE = 16;

	private static SecureRandom random = new SecureRandom();

	// -- HMAC-SHA1 funciton --//
	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * 
	 * @param input
	 *            原始输入字符数组
	 * @param key
	 *            HMAC-SHA1密钥
	 */
	public static byte[] hmacSha1(byte[] input, byte[] key) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, HMACSHA1);
			Mac mac = Mac.getInstance(HMACSHA1);
			mac.init(secretKey);
			return mac.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 校验HMAC-SHA1签名是否正确.
	 * 
	 * @param expected
	 *            已存在的签名
	 * @param input
	 *            原始输入字符串
	 * @param key
	 *            密钥
	 */
	public static boolean isMacValid(byte[] expected, byte[] input, byte[] key) {
		byte[] actual = hmacSha1(input, key);
		return Arrays.equals(expected, actual);
	}

	/**
	 * 生成HMAC-SHA1密钥,返回字节数组,长度为160位(20字节). HMAC-SHA1算法对密钥无特殊要求,
	 * RFC2401建议最少长度为160位(20字节).
	 */
	public static byte[] generateHmacSha1Key() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(HMACSHA1);
			keyGenerator.init(DEFAULT_HMACSHA1_KEYSIZE);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	// -- AES funciton --//
	/**
	 * 使用AES加密原始字符串.
	 * 
	 * @param input
	 *            原始输入字符数组
	 * @param key
	 *            符合AES要求的密钥
	 */
	public static byte[] aesEncrypt(byte[] input, byte[] key) {
		return aes(input, key, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 使用AES加密原始字符串.
	 * 
	 * @param input
	 *            原始输入字符数组
	 * @param key
	 *            符合AES要求的密钥
	 * @param iv
	 *            初始向量
	 */
	public static byte[] aesEncrypt(byte[] input, byte[] key, byte[] iv) {
		return aes(input, key, iv, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 使用AES解密字符串, 返回原始字符串.
	 * 
	 * @param input
	 *            Hex编码的加密字符串
	 * @param key
	 *            符合AES要求的密钥
	 */
	public static String aesDecrypt(byte[] input, byte[] key) {
		byte[] decryptResult = aes(input, key, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * 使用AES解密字符串, 返回原始字符串.
	 * 
	 * @param input
	 *            Hex编码的加密字符串
	 * @param key
	 *            符合AES要求的密钥
	 * @param iv
	 *            初始向量
	 */
	public static String aesDecrypt(byte[] input, byte[] key, byte[] iv) {
		byte[] decryptResult = aes(input, key, iv, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 * 
	 * @param input
	 *            原始字节数组
	 * @param key
	 *            符合AES要求的密钥
	 * @param mode
	 *            Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] aes(byte[] input, byte[] key, int mode) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, AES);
			Cipher cipher = Cipher.getInstance(AES);
			cipher.init(mode, secretKey);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 * 
	 * @param input
	 *            原始字节数组
	 * @param key
	 *            符合AES要求的密钥
	 * @param iv
	 *            初始向量
	 * @param mode
	 *            Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, AES);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance(AES_CBC);
			cipher.init(mode, secretKey, ivSpec);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 生成AES密钥,返回字节数组, 默认长度为128位(16字节).
	 */
	public static byte[] generateAesKey() {
		return generateAesKey(DEFAULT_AES_KEYSIZE);
	}

	/**
	 * 生成AES密钥,可选长度为128,192,256位.
	 */
	public static byte[] generateAesKey(int keysize) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
			keyGenerator.init(keysize);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 生成随机向量,默认大小为cipher.getBlockSize(), 16字节.
	 */
	public static byte[] generateIV() {
		byte[] bytes = new byte[DEFAULT_IVSIZE];
		random.nextBytes(bytes);
		return bytes;
	}

	public static void main(String[] args) {
		String result = Cryptos.desEcb("8FD532AFDE857C8C56393CC7E8C1D0EC", "8FD532AFDE857C8C56393CC7E8C1D0EC");
		System.out.println(result);
		System.out.println(Cryptos.desDcb("8FD532AFDE857C8C56393CC7E8C1D0EC", result));
	}

	public static String desDcb(String key, String srcData) {
		try {
			if (key.length() != 8 * 2 && key.length() != 16 * 2 && key.length() != 24 * 2) {
				throw Exceptions.unchecked(new GeneralSecurityException("KEY长度 不正确"));
			}
			if (key.length() == 16 * 2) {
				key += key.substring(0, 16);
			}
			SecretKey keySpec = new SecretKeySpec(hexToBytes(key), "DESede");// key
			Cipher enc = Cipher.getInstance("DESede/ECB/NoPadding");
			enc.init(Cipher.DECRYPT_MODE, keySpec);
			byte[] temp = enc.doFinal(hexToBytes(srcData));
			return byteToHex(temp, 0, temp.length);
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	public static String desEcb(String key, String srcData) {
		try {
			if (key.length() != 8 * 2 && key.length() != 16 * 2 && key.length() != 24 * 2) {
				throw Exceptions.unchecked(new GeneralSecurityException("KEY长度 不正确"));
			}
			if (key.length() == 16 * 2) {
				key += key.substring(0, 16);
			}
			SecretKey keySpec = new SecretKeySpec(hexToBytes(key), "DESede");// key
			Cipher enc = Cipher.getInstance("DESede/ECB/NoPadding");
			enc.init(Cipher.ENCRYPT_MODE, keySpec);
			byte[] temp = enc.doFinal(hexToBytes(srcData));
			return byteToHex(temp, 0, temp.length);
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
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

}