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

import com.whty.platform.common.base.web.BaseController;
import com.whty.platform.common.config.Global;
import com.whty.platform.common.persistence.Page;
import com.whty.platform.modules.bussiness.entity.ProfitSet;
import com.whty.platform.modules.bussiness.service.ProfitSetService;

/**
 * 分润 Controller
 * 
 * @author 舒海洋
 * @version 2013-10-15
 */
@Controller
@RequestMapping(value = "${adminPath}/operation/profitSet")
public class ProfitSetController extends BaseController {

	@Autowired
	private ProfitSetService profitSetService;

	@ModelAttribute
	public ProfitSet get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return profitSetService.get(id);
		} else {
			return new ProfitSet();
		}
	}

	@RequiresPermissions("operation:profitSet:view")
	@RequestMapping(value = { "list", "" })
	public String list(ProfitSet profitSet, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProfitSet> page = profitSetService.find(new Page<ProfitSet>(request, response), profitSet);
		model.addAttribute("page", page);
		return "modules/profit/profitSetList";
	}

	@RequiresPermissions("operation:profitSet:view")
	@RequestMapping(value = "form")
	public String form(ProfitSet profitSet, Model model) {
		profitSet.setProfitRegions(profitSetService.getRegions(profitSet.getId()));
		model.addAttribute("profitSet", profitSet);
		return "modules/profit/profitSetForm";
	}

	@RequiresPermissions("operation:profitSet:edit")
	@RequestMapping(value = "save")
	public String save(ProfitSet profitSet, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, profitSet)) {
			return form(profitSet, model);
		}
		profitSetService.save(profitSet);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:" + Global.getAdminPath() + "/operation/profitSet/?repage";
	}

	@RequiresPermissions("operation:profitSet:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		profitSetService.delete(id);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + Global.getAdminPath() + "/operation/profitSet/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "checkType")
	public String checkType(String oldBusinessType, String businessType) {
		if (businessType != null && businessType.equals(oldBusinessType)) {
			return "true";
		} else if (businessType != null && profitSetService.getProfitSetByBusinessType(businessType) == null) {
			return "true";
		}
		return "false";
	}
}
