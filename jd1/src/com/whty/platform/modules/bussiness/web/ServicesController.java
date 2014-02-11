/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.web;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whty.platform.common.base.web.BaseController;
import com.whty.platform.common.config.Global;
import com.whty.platform.common.persistence.Page;
import com.whty.platform.modules.bussiness.entity.Provider;
import com.whty.platform.modules.bussiness.entity.Services;
import com.whty.platform.modules.bussiness.service.ProviderService;
import com.whty.platform.modules.bussiness.service.ServicesService;
import com.whty.platform.modules.sys.entity.User;
import com.whty.platform.modules.sys.utils.UserUtils;

/**
 * 服务Controller
 * 
 * @author qimin
 * @version 2013-07-10
 */
@Controller
@RequestMapping(value = "${adminPath}/bussiness/services")
public class ServicesController extends BaseController {

	@Autowired
	private ServicesService servicesService;

	@Autowired
	private ProviderService providerService;

	@ModelAttribute
	public Services get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return servicesService.get(id);
		} else {
			return new Services();
		}
	}

	@RequiresPermissions("bussiness:services:view")
	@RequestMapping(value = { "list", "" })
	public String list(Services services, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			// services.setUser(user);
		}
		Page<Services> page = servicesService.find(new Page<Services>(request, response), services);
		model.addAttribute("page", page);

		Provider provider = new Provider();
		List<Provider> providers = providerService.find(provider);
		model.addAttribute("providers", providers);

		return "modules/bussiness/servicesList";
	}

	@RequiresPermissions("bussiness:services:view")
	@RequestMapping(value = "form")
	public String form(Services services, Model model) {
		if (services.getProvider() != null && services.getProvider().getId() > 0) {
			services.setProvider(providerService.get(services.getProvider().getId()));
		}
		Provider provider = new Provider();
		List<Provider> providers = providerService.find(provider);
		model.addAttribute("services", services);
		model.addAttribute("providers", providers);
		return "modules/bussiness/servicesForm";
	}

	@RequiresPermissions("bussiness:services:edit")
	@RequestMapping(value = "save")
	public String save(Services services, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, services)) {
			return form(services, model);
		}
		services.setCode(services.getBussinessType());
		servicesService.saveServices(services);
		addMessage(redirectAttributes, "保存服务'" + services.getName() + "'成功");
		return "redirect:" + Global.getAdminPath() + "/bussiness/services/?repage";
	}

	@RequiresPermissions("bussiness:services:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		servicesService.delete(id);
		addMessage(redirectAttributes, "删除服务成功");
		return "redirect:" + Global.getAdminPath() + "/bussiness/services/?repage";
	}

}
