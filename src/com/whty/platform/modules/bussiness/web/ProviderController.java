/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.web;

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

import com.whty.platform.common.config.Global;
import com.whty.platform.common.persistence.Page;
import com.whty.platform.common.base.web.BaseController;
import com.whty.platform.modules.sys.entity.User;
import com.whty.platform.modules.sys.utils.UserUtils;
import com.whty.platform.modules.bussiness.entity.Provider;
import com.whty.platform.modules.bussiness.service.ProviderService;

/**
 * 服务提供方Controller
 * 
 * @author qimin
 * @version 2013-07-10
 */
@Controller
@RequestMapping(value = "${adminPath}/bussiness/provider")
public class ProviderController extends BaseController {

	@Autowired
	private ProviderService providerService;

	@ModelAttribute
	public Provider get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return providerService.get(id);
		} else {
			return new Provider();
		}
	}

	@RequiresPermissions("bussiness:provider:view")
	@RequestMapping(value = { "list", "" })
	public String list(Provider provider, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			// provider.setUser(user);
		}
		Page<Provider> page = providerService.find(new Page<Provider>(request, response), provider);
		model.addAttribute("page", page);
		return "modules/bussiness/providerList";
	}

	@RequiresPermissions("bussiness:provider:view")
	@RequestMapping(value = "form")
	public String form(Provider provider, Model model) {
		model.addAttribute("provider", provider);
		return "modules/bussiness/providerForm";
	}

	@RequiresPermissions("bussiness:provider:edit")
	@RequestMapping(value = "save")
	public String save(Provider provider, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, provider)) {
			return form(provider, model);
		}
		providerService.save(provider);
		addMessage(redirectAttributes, "保存服务提供方'" + provider.getName() + "'成功");
		return "redirect:" + Global.getAdminPath() + "/bussiness/provider/?repage";
	}

	@RequiresPermissions("bussiness:provider:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		if (providerService.checkdelete(id)) {
			providerService.delete(id);
			addMessage(redirectAttributes, "删除服务提供方成功");
		} else {
			addMessage(redirectAttributes, "删除失败，该供应商已经正式签约，不可以被删除");
		}
		return "redirect:" + Global.getAdminPath() + "/bussiness/provider/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "checkUsername")
	public String checkUsername(String oldUserName, String code) {
		if (code != null && code.equals(oldUserName)) {
			return "true";
		} else if (code != null && providerService.findByUsername(code) == null) {
			return "true";
		}
		return "false";
	}

}
