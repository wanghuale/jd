package com.whty.platform.modules.front.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.whty.platform.common.base.web.BaseController;
import com.whty.platform.common.utils.security.MD5;
import com.whty.platform.modules.bussiness.entity.Consumer;
import com.whty.platform.modules.bussiness.service.ConsumerService;
import com.whty.platform.modules.front.utils.FrontUserUtils;

/**
 * 前台用户登录Controller
 */
@Controller
@RequestMapping(value = FrontUserUtils.FRONT_PATH)
public class FrontLoginController extends BaseController {
	@Autowired
	private ConsumerService consumerService;

	/**
	 * 管理登录
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		Consumer user = FrontUserUtils.getSession(request);
		if (user != null) {
			return "redirect:" + getIndexUrl();
		}
		return getLoginUrl();
	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		model.addAttribute("username", username);
		Consumer user = FrontUserUtils.getSession(request);
		if (user == null) {
			user = consumerService.getConsumerByName(username);
			if (user != null) {
				/**
				 * 判断密码是否正确（密码暂未加密）
				 */
				if (MD5.MD5String(password).equals(user.getPassword())) {
					// 验证成功
					FrontUserUtils.addSession(request, user);
					return "redirect:" + getIndexUrl();
				} else {
					// 登陆失败，密码错误
					model.addAttribute("loginError", "error2");
					return getLoginUrl();
				}
			} else {
				// 登录失败,用户名不存在
				model.addAttribute("loginError", "error1");
				return getLoginUrl();
			}
		} else {// 已登录
			return "redirect:" + getIndexUrl();
		}
	}

	/**
	 * 注销
	 */
	@RequestMapping(value = "logon", method = RequestMethod.GET)
	public String logon(HttpServletRequest request, HttpServletResponse response, Model model) {
		Consumer user = FrontUserUtils.getSession(request);
		if (user != null) {
			FrontUserUtils.removeSession(request);
		}
		request.setAttribute("ISFRONT", "true");
		return getLoginUrl();
	}

	/**
	 * 主页
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/front/index";
	}

	/**
	 * 获取登录页面
	 */
	private String getLoginUrl() {
		return "modules/sys/sysLogin";
	}

	/**
	 * 获取注册页面
	 */
	private String getIndexUrl() {
		return FrontUserUtils.FRONT_PATH + "/index";
	}

}
