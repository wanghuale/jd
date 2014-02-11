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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whty.platform.common.config.Global;
import com.whty.platform.common.persistence.Page;
import com.whty.platform.common.base.web.BaseController;
import com.whty.platform.modules.sys.entity.User;
import com.whty.platform.modules.sys.utils.UserUtils;
import com.whty.platform.modules.bussiness.entity.Localservices;
import com.whty.platform.modules.bussiness.service.LocalservicesService;

/**
 * 本地服务Controller
 * @author qimin
 * @version 2013-07-10
 */
@Controller
@RequestMapping(value = "${adminPath}/bussiness/localservices")
public class LocalservicesController extends BaseController {

	@Autowired
	private LocalservicesService localservicesService;
	
	@ModelAttribute
	public Localservices get(@RequestParam(required=false) Long id) {
		if (id != null){
			return localservicesService.get(id);
		}else{
			return new Localservices();
		}
	}
	
	@RequiresPermissions("bussiness:localservices:view")
	@RequestMapping(value = {"list", ""})
	public String list(Localservices localservices, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			//localservices.setUser(user);
		}
        Page<Localservices> page = localservicesService.find(new Page<Localservices>(request, response), localservices); 
        model.addAttribute("page", page);
		return "modules/bussiness/localservicesList";
	}

	@RequiresPermissions("bussiness:localservices:view")
	@RequestMapping(value = "form")
	public String form(Localservices localservices, Model model) {
		model.addAttribute("localservices", localservices);
		return "modules/bussiness/localservicesForm";
	}

	@RequiresPermissions("bussiness:localservices:edit")
	@RequestMapping(value = "save")
	public String save(Localservices localservices, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, localservices)){
			return form(localservices, model);
		}
		localservicesService.save(localservices);
		addMessage(redirectAttributes, "保存本地服务'" + localservices.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/bussiness/localservices/?repage";
	}
	
	@RequiresPermissions("bussiness:localservices:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		localservicesService.delete(id);
		addMessage(redirectAttributes, "删除本地服务成功");
		return "redirect:"+Global.getAdminPath()+"/bussiness/localservices/?repage";
	}

}
