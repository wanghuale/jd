/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.front.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whty.platform.common.base.web.BaseController;
import com.whty.platform.common.utils.StringUtils;
import com.whty.platform.common.utils.security.MD5;
import com.whty.platform.modules.bussiness.entity.Consumer;
import com.whty.platform.modules.bussiness.service.ConsumerService;
import com.whty.platform.modules.front.entity.FrontUser;
import com.whty.platform.modules.front.service.FrontUserService;
import com.whty.platform.modules.front.utils.FrontUserUtils;

/**
 * 服务消费者Controller
 */
@Controller
@RequestMapping(value = FrontUserUtils.FRONT_PATH + "/info")
public class InfoController extends BaseController {

	@Autowired
	private ConsumerService consumerService;
	@Autowired
	private FrontUserService frontUserService;

	@ModelAttribute
	public Consumer get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return consumerService.get(id);
		} else {
			return new Consumer();
		}
	}

	@RequestMapping(value = "")
	public String info(Consumer consumer, Model model) {
		model.addAttribute("consumer", consumer);
		return "modules/front/info";
	}

	@RequestMapping(value = "modifyPWD")
	public String form(Consumer consumer, Model model) {
		model.addAttribute("consumer", consumer);
		return "modules/front/modifyPwd";
	}

	@RequestMapping(value = "save")
	public String save(Consumer consumer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, consumer)) {
			return form(consumer, model);
		}
		consumerService.save(consumer);
		addMessage(redirectAttributes, "密码修改成功");
		return "redirect:" + FrontUserUtils.FRONT_PATH + "/info/form?repage";
	}

	@RequestMapping(value = "savePWD")
	public String modifyPwd(String oldPassword, String newPassword, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		Consumer consumer = FrontUserUtils.getSession(request);
		FrontUser temp = frontUserService.get(consumer.getId());
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {
			if (MD5.MD5String(oldPassword).equals(temp.getPassword())) {
				temp.setPassword(MD5.MD5String(newPassword));
				frontUserService.save(temp);
				addMessage(redirectAttributes, "修改密码成功");
			} else {
				addMessage(redirectAttributes, "修改密码失败，旧密码错误");
			}
		}
		return "redirect:" + FrontUserUtils.FRONT_PATH + "/info/modifyPWD?repage";
	}
}
