/**
 * 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whty.platform.modules.front.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.whty.platform.modules.bussiness.entity.Consumer;

/**
 * 用户工具类
 */
@Service
public class FrontUserUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;
	public static final String FRONT_USER = "FRONT_USER";// 用户信息
	public static final String FRONT_PATH = "/front";// 用户信息

	public void setApplicationContext(ApplicationContext applicationContext) {
		FrontUserUtils.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 登录验证后保存信息
	 */
	public static void addSession(HttpServletRequest request, Consumer user) {
		request.getSession().setAttribute(FRONT_USER, user);
	}

	/**
	 * 获取注销当前用户信息
	 */
	public static void removeSession(HttpServletRequest request) {
		request.getSession().removeAttribute(FRONT_USER);
	}

	/**
	 * 获取当前登录用户
	 */
	public static Consumer getSession(HttpServletRequest request) {
		Consumer user = (Consumer) request.getSession().getAttribute(FRONT_USER);
		return user;
	}

	public static String getFrontPath() {
		return FRONT_PATH;
	}

}
