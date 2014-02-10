/**
 * Copyright &copy; 2012-2013 <a href="www.whty.com.cn">whty</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.whty.platform.modules.sys.web;

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
import com.whty.platform.modules.luojia.service.LuoJiaDBService;
import com.whty.platform.modules.sys.entity.Dict;
import com.whty.platform.modules.sys.service.DictService;

/**
 * 字典Controller
 * @author 舒海洋
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/dict")
public class DictController extends BaseController {

	@Autowired
	private DictService dictService;
	
	
	@ModelAttribute
	public Dict get(@RequestParam(required=false) Long id) {
		if (id != null){
			return dictService.get(id);
		}else{
			return new Dict();
		}
	}
	
	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = {"list", ""})
	public String list(Dict dict, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<String> typeList = dictService.findTypeList();
		model.addAttribute("typeList", typeList);
        Page<Dict> page = dictService.find(new Page<Dict>(request, response), dict); 
        model.addAttribute("page", page);
		return "modules/sys/dictList";
	}

	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = "form")
	public String form(Dict dict, Model model) {
		model.addAttribute("dict", dict);
		return "modules/sys/dictForm";
	}

	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "save")//@Valid 
	public String save(Dict dict,String edite,Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, dict)){
			return form(dict, model);
		}
		String redirect = "";
		if(edite != null && edite.equals("true")){//修改		
			redirect = "redirect:"+Global.getAdminPath()+"/sys/dict/?repage";
		}else{
			//List<Dict> list = dictService.findByValue(dict.getValue());
			List<Dict> list = dictService.findByValueT(dict.getValue(),dict.getType());
			if(list != null && list.size() > 0){
				addMessage(redirectAttributes, "保存字典'" + dict.getLabel() + "'失败, 键值重复");
				return "redirect:"+Global.getAdminPath()+"/sys/dict/?repage";
			}			
			redirect =  "redirect:"+Global.getAdminPath()+"/sys/dict/?repage&type="+dict.getType();
		}
		dictService.save(dict);
		addMessage(redirectAttributes, "保存字典'" + dict.getLabel() + "'成功");
		return redirect;
	}
	
	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		dictService.delete(id);
		addMessage(redirectAttributes, "删除字典成功");
		return "redirect:"+Global.getAdminPath()+"/sys/dict/?repage";
	}


}
