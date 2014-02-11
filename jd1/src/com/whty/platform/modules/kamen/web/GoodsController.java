/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.kamen.web;

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
import com.whty.platform.common.utils.RenderUtils;
import com.whty.platform.modules.kamen.entity.Goods;
import com.whty.platform.modules.kamen.service.GoodsService;
import com.whty.platform.modules.kamen.service.KemenDBService;

/**
 * 商品Controller
 * 
 * @author 舒海洋
 * @version 2013-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/kamen/goods")
public class GoodsController extends BaseController {

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private KemenDBService kemenDBService;

	@ModelAttribute
	public Goods get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return goodsService.get(id);
		} else {
			return new Goods();
		}
	}

	@RequiresPermissions("kamen:goods:view")
	@RequestMapping(value = { "list", "" })
	public String list(Goods goods, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Goods> page = goodsService.findByRequirement(new Page<Goods>(request, response), goods);

		model.addAttribute("page", page);
		return "modules/kamen/goodsList";
	}

	@RequestMapping(value = "updateSellPrice")
	public String updateSellPrice(GoodsVo vo, RedirectAttributes redirectAttributes, Goods goods, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		for (Goods g : vo.getGoodlist()) {
			if (g != null && g.getId() != null) {
				Goods temp = goodsService.get(g.getId());
				temp.setSellPrice(g.getSellPrice());
				double spreadprice = Double.parseDouble(temp.getSellPrice()) - Double.parseDouble(temp.getSellPrice());// 销售价格-进价
				temp.setSpreadprice(spreadprice);
				goodsService.save(temp);
			}
		}
		addMessage(redirectAttributes, "保存销售价成功!");
		Page<Goods> page = goodsService.findByRequirement(new Page<Goods>(request, response), goods);
		model.addAttribute("page", page);
		return "modules/kamen/goodsList";
	}

	@RequiresPermissions("kamen:goods:view")
	@RequestMapping(value = "form")
	public String form(Goods goods, Model model) {
		model.addAttribute("goods", goods);
		return "modules/kamen/goodsForm";
	}

	@RequiresPermissions("kamen:goods:edit")
	@RequestMapping(value = "save")
	public String save(Goods goods, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goods)) {
			return form(goods, model);
		}
		goodsService.save(goods);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:" + Global.getAdminPath() + "/modules/kamen/goods/?repage";
	}

	@RequiresPermissions("kamen:goods:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		goodsService.delete(id);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + Global.getAdminPath() + "/modules/kamen/goods/?repage";
	}

	@RequestMapping(value = "updategoodfromkamen")
	public void updategoodfromkamen(HttpServletRequest request, HttpServletResponse response) {
		try {
			kemenDBService.saveGoods();
			RenderUtils.renderJson(response, "true");
		} catch (Exception e) {
			RenderUtils.renderJson(response, "false");
		}
	}
}
