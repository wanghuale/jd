package com.whty.platform.common.utils;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 提供前台跟后台交互的工具类
 */
public class RenderUtils {
	private static Logger logger = LoggerFactory.getLogger(RenderUtils.class);

	// -- header 常量定义 --//
	@SuppressWarnings("unused")
	private static final String HEADER_ENCODING = "encoding";
	@SuppressWarnings("unused")
	private static final String HEADER_NOCACHE = "no-cache";
	private static final String DEFAULT_ENCODING = "UTF-8";
	@SuppressWarnings("unused")
	private static final boolean DEFAULT_NOCACHE = true;

	// -- Content Type 定义 --//
	public static final String TEXT_TYPE = "text/plain";
	public static final String JSON_TYPE = "application/json";
	public static final String XML_TYPE = "text/xml";
	public static final String HTML_TYPE = "text/html";
	public static final String JS_TYPE = "text/javascript";
	public static final String EXCEL_TYPE = "application/vnd.ms-excel";

	/**
	 * 发送文本，用于form提交
	 * 
	 * @author XiaShenBao
	 * @date 2013-5-17
	 * @version 1.0
	 * @param response
	 * @param bl
	 * @param msg
	 */
	public static void renderAjax(HttpServletResponse response, Boolean bl, String msg) {
		renderJson(response, "{success:" + bl + ",msg:'" + msg + "'}");
	}

	/**
	 * 发送文本。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderText(HttpServletResponse response, String text) {
		render(response, TEXT_TYPE + ";charset=" + DEFAULT_ENCODING, text);
	}

	/**
	 * 发送json。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderJson(HttpServletResponse response, String text) {
		render(response, JSON_TYPE + ";charset=" + DEFAULT_ENCODING, text);
	}

	/**
	 * 发送xml。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderXml(HttpServletResponse response, String text) {
		render(response, XML_TYPE + ";charset=" + DEFAULT_ENCODING, text);
	}

	/**
	 * 发送HTML。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderHtml(HttpServletResponse response, String text) {
		render(response, HTML_TYPE + ";charset=" + DEFAULT_ENCODING, text);
	}

	/**
	 * 发送Js。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderJs(HttpServletResponse response, String text) {
		render(response, JS_TYPE + ";charset=" + DEFAULT_ENCODING, text);
	}

	/**
	 * 发送Excel。使用UTF-8编码。
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param text
	 *            发送的字符串
	 */
	public static void renderExcel(HttpServletResponse response, String text) {
		render(response, EXCEL_TYPE + ";charset=" + DEFAULT_ENCODING, text);
	}

	/**
	 * 发送内容。使用UTF-8编码。
	 * 
	 * @param response
	 * @param contentType
	 * @param text
	 */
	private static void render(HttpServletResponse response, String contentType, String text) {
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write(text);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取客户端IP地址
	 */
	@SuppressWarnings("rawtypes")
	public static String getIpAddress(HttpServletRequest request) {
		if (logger.isDebugEnabled()) {
			StringBuffer buf = new StringBuffer("all head info:\n");
			Enumeration enumeration = request.getHeaderNames();
			while (enumeration.hasMoreElements()) {
				Object head = enumeration.nextElement();
				if (null != head) {
					String value = request.getHeader(String.valueOf(head));
					buf.append(head + "=" + value + "\n");
				}
			}
			logger.debug(buf.toString());
		}
		String ip = request.getHeader("x-forwarded-for");
		logger.debug("request.getHeader(\"x-forwarded-for\")=" + ip);

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
			logger.debug("request.getHeader(\"X-Forwarded-For\")=" + ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			logger.debug("request.getHeader(\"Proxy-Client-IP\")=" + ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			logger.debug("request.getHeader(\"WL-Proxy-Client-IP\")=" + ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			logger.debug("request.getHeader(\"HTTP_CLIENT_IP\")=" + ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			logger.debug("request.getHeader(\"HTTP_X_FORWARDED_FOR\")=" + ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			logger.debug("request.getRemoteAddr()=" + ip);
		}

		if (null != ip && ip.indexOf(',') != -1) {
			// 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串 IP 值
			// 取X-Forwarded-For中第一个非unknown的有效IP字符串
			// 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
			// 192.168.1.100
			// 用户真实IP为： 192.168.1.110
			// 注意:当访问地址为 localhost 时 地址格式为 0:0:0:0:0:0:1
			logger.debug("ip=" + ip);
			String[] ips = ip.split(",");
			for (int i = 0; i < ips.length; i++) {
				if (null != ips[i] && !"unknown".equalsIgnoreCase(ips[i])) {
					ip = ips[i];
					break;
				}
			}
			if ("0:0:0:0:0:0:1".equals(ip)) {
				logger.warn("由于客户端访问地址使用 localhost，获取客户端真实IP地址错误，请使用IP方式访问");
			}
		}

		if ("unknown".equalsIgnoreCase(ip)) {
			logger.warn("由于客户端通过Squid反向代理软件访问，获取客户端真实IP地址错误，请更改squid.conf配置文件forwarded_for项默认是为on解决");
		}
		return ip;
	}
}
