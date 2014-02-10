package com.whty.platform.modules.front.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.whty.platform.common.config.Global;

/**
 * 前台拦截器
 */
public class FrontInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory.getLogger(FrontInterceptor.class);

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String uriPrefix = request.getContextPath() + Global.getAdminPath();
		logger.debug(uriPrefix);
	}

}
