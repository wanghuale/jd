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
import com.whty.platform.modules.bussiness.entity.ServiceRecord;
import com.whty.platform.modules.bussiness.service.ServiceRecordService;

/**
 * 服务Controller
 * 
 * @author qimin
 * @version 2013-07-16
 */
@Controller
@RequestMapping(value = "${adminPath}/bussiness/serviceRecord")
public class ServiceRecordController extends BaseController {

	@Autowired
	private ServiceRecordService serviceRecordService;

	@ModelAttribute
	public ServiceRecord get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return serviceRecordService.get(id);
		} else {
			return new ServiceRecord();
		}
	}

	@RequiresPermissions("bussiness:serviceRecord:view")
	@RequestMapping(value = { "list", "" })
	public String list(ServiceRecord serviceRecord, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			// serviceRecord.setUser(user);
		}
		Page<ServiceRecord> page = serviceRecordService.find(new Page<ServiceRecord>(request, response), serviceRecord);
		model.addAttribute("page", page);
		return "modules/bussiness/serviceRecordList";
	}

	@RequiresPermissions("bussiness:serviceRecord:view")
	@RequestMapping(value = "form")
	public String form(ServiceRecord serviceRecord, Model model) {
		model.addAttribute("serviceRecord", serviceRecord);
		return "modules/bussiness/serviceRecordForm";
	}

	@RequiresPermissions("bussiness:serviceRecord:edit")
	@RequestMapping(value = "save")
	public String save(ServiceRecord serviceRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, serviceRecord)) {
			return form(serviceRecord, model);
		}
		serviceRecordService.save(serviceRecord);
		addMessage(redirectAttributes, "保存服务'" + serviceRecord.getUsername() + "'成功");
		return "redirect:" + Global.getAdminPath() + "/modules/bussiness/serviceRecord/?repage";
	}

	@RequiresPermissions("bussiness:serviceRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		serviceRecordService.delete(id);
		addMessage(redirectAttributes, "删除服务成功");
		return "redirect:" + Global.getAdminPath() + "/modules/bussiness/serviceRecord/?repage";
	}

}
