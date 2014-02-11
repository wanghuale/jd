package com.whty.platform.modules.front.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.whty.platform.modules.bussiness.entity.Consumer;
import com.whty.platform.modules.front.utils.FrontUserUtils;

/**
 * 前台过滤器
 */
public class FrontFilter implements javax.servlet.Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		Consumer user = FrontUserUtils.getSession(req);
		String uri = req.getRequestURI();
		// 判断用户是否登录，进行页面的处理
		if (null == user && !uri.endsWith(FrontUserUtils.FRONT_PATH + "/login")) {
			// 未登录用户，重定向到登录页面
			((HttpServletResponse) response).sendRedirect(req.getContextPath() + FrontUserUtils.FRONT_PATH + "/login");
		} else {
			// 已登录用户，允许访问
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
