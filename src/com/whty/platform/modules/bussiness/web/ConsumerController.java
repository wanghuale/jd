/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whty.platform.common.base.web.BaseController;
import com.whty.platform.common.config.Global;
import com.whty.platform.common.persistence.Page;
import com.whty.platform.common.utils.DateUtils;
import com.whty.platform.common.utils.security.MD5;
import com.whty.platform.modules.bussiness.entity.Consumer;
import com.whty.platform.modules.bussiness.entity.Provider;
import com.whty.platform.modules.bussiness.entity.Services;
import com.whty.platform.modules.bussiness.service.ConsumerService;
import com.whty.platform.modules.bussiness.service.ProviderService;
import com.whty.platform.modules.bussiness.service.ServicesService;
import com.whty.platform.modules.sys.entity.User;
import com.whty.platform.modules.sys.utils.UserUtils;

/**
 * 服务消费者Controller
 * 
 * @author qimin
 * @version 2013-07-10
 */
@Controller
@RequestMapping(value = "${adminPath}/bussiness/consumer")
public class ConsumerController extends BaseController {

	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private ServicesService servicesService;

	@Autowired
	private ProviderService providerService;

	@ModelAttribute
	public Consumer get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return consumerService.get(id);
		} else {
			return new Consumer();
		}
	}

	@RequiresPermissions("bussiness:consumer:view")
	@RequestMapping(value = { "list", "" })
	public String list(Consumer consumer, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			// consumer.setUser(user);
		}

		String endDate = DateUtils.formatDate(DateUtils.getNextDay(new Date()), "yyyy-MM-dd");
		model.addAttribute("endDate", endDate);

		String beginDate = DateUtils.formatDate(DateUtils.getFirstDay(new Date()), "yyyy-MM-dd");
		model.addAttribute("beginDate", beginDate);

		Page<Consumer> page = consumerService.find(new Page<Consumer>(request, response), consumer);
		model.addAttribute("page", page);
		return "modules/bussiness/consumerList";
	}

	@RequiresPermissions("bussiness:consumer:view")
	@RequestMapping(value = "form")
	public String form(Consumer consumer, Model model) {
		model.addAttribute("consumer", consumer);
		model.addAttribute("serviceList", servicesService.find(new Services()));

		List<Provider> providers = providerService.find(new Provider());
		model.addAttribute("providers", providers);

		return "modules/bussiness/consumerForm";
	}

	@RequiresPermissions("bussiness:consumer:edit")
	@RequestMapping(value = "resetpwd")
	public String resetpwd(Consumer consumer, Model model, RedirectAttributes redirectAttributes) {
		if (consumer.getId() != null) {
			Consumer temp = consumerService.get(consumer.getId());
			temp.setPassword(MD5.MD5String("888888"));
			temp.setCode(consumer.getUsername());
			consumerService.save(temp);
			addMessage(redirectAttributes, consumer.getName() + "重置密码成功");
		} else {
			addMessage(redirectAttributes, "重置密码失败");
		}
		return "redirect:" + Global.getAdminPath() + "/bussiness/consumer/?repage";
	}

	@RequiresPermissions("bussiness:consumer:edit")
	@RequestMapping(value = "save")
	public String save(Consumer consumer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, consumer)) {
			return form(consumer, model);
		}
		if (consumer.getId() == null) {
			consumer.setPassword(MD5.MD5String("888888"));
		}
		consumerService.save(consumer);
		addMessage(redirectAttributes, "保存服务消费者'" + consumer.getName() + "'成功");
		return "redirect:" + Global.getAdminPath() + "/bussiness/consumer/?repage";
	}

	@RequiresPermissions("bussiness:consumer:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		consumerService.delete(id);
		addMessage(redirectAttributes, "删除服务消费者成功");
		return "redirect:" + Global.getAdminPath() + "/bussiness/consumer/?repage";
	}

	@RequiresPermissions("bussiness:consumer:viewkey")
	@RequestMapping(value = "createkey")
	public String createkey(Consumer consumer, Model model) {
		model.addAttribute("key", consumer.getkey());
		return "modules/bussiness/consumerKey";
	}

	@ResponseBody
	@RequestMapping(value = "checkUsername")
	public String checkUsername(String oldUserName, String username) {
		if (username != null && username.equals(oldUserName)) {
			return "true";
		} else if (username != null && consumerService.findByUsername(username) == null) {
			return "true";
		}
		return "false";
	}

}
