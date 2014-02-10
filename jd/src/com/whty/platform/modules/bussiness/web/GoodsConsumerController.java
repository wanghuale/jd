/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
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
import com.whty.platform.modules.bussiness.entity.Consumer;
import com.whty.platform.modules.bussiness.entity.GoodsConsumer;
import com.whty.platform.modules.bussiness.service.ConsumerService;
import com.whty.platform.modules.bussiness.service.GoodsConsumerService;
import com.whty.platform.modules.kamen.entity.Goods;
import com.whty.platform.modules.kamen.service.GoodsService;
import com.whty.platform.modules.sp.entity.SpConsts;

/**
 * 对账Controller
 * 
 * @author 舒海洋
 * @version 2013-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/bussiness/goodsConsumer")
public class GoodsConsumerController extends BaseController {

	@Autowired
	private GoodsConsumerService goodsConsumerService;

	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private GoodsService goodsService;

	@ModelAttribute
	public GoodsConsumer get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return goodsConsumerService.get(id);
		} else {
			return new GoodsConsumer();
		}
	}

	@RequestMapping(value = "goodstree")
	public String goodstree(GoodsConsumer goodsConsumer, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("pgoods", SpConsts.p_goods_all);
		model.addAttribute("treelist", goodsService.find());
		List<Goods> list = goodsService.findbylist(new Goods());
		model.addAttribute("goodsalll", list);
		model.addAttribute("goodsids", goodsConsumerService.findGoodIDs(goodsConsumer.getConsumer().getId()));
		return "modules/bussiness/goodsConsumerForm2";
	}

	@RequestMapping(value = "addgoods")
	public String addgoods(GoodsConsumer goodsConsumer, Goods goods, HttpServletRequest request, HttpServletResponse response, Model model) {
		Consumer consumer = consumerService.get(goodsConsumer.getConsumer().getId());
		model.addAttribute("consumer", consumer);
		Page<Goods> page = goodsService.find(new Page<Goods>(request, response), goods);
		model.addAttribute("page", page);
		return "modules/bussiness/goodsConsumerForm";
	}

	@RequestMapping(value = "addll")
	public String addll(GoodsConsumer goodsConsumer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Consumer consumer = consumerService.get(goodsConsumer.getConsumer().getId());
		List<Goods> list = goodsService.findbylist(new Goods());
		if (list != null) {
			for (Goods g : list) {
				GoodsConsumer gc = goodsConsumerService.getGoodsConcumerByGIDandCID(g.getId(), consumer.getId());
				if (gc.getId() == null) {
					gc.setGoods(g);
					gc.setSell_price(NumberUtils.toDouble(g.getSellPrice()));
					gc.setSpreadprice(g.getSpreadprice());
					gc.setConsumer(consumer);
					goodsConsumerService.save(gc);
				}
			}
		}
		return "redirect:" + Global.getAdminPath() + "/bussiness/goodsConsumer/list?consumer.id=" + goodsConsumer.getConsumer().getId();
	}

	@RequestMapping(value = "savegoods")
	public String savegoods(GoodsConsumer goodsConsumer, Goods goods, HttpServletRequest request, HttpServletResponse response, Model model) {
		String goodsIds = request.getParameter("goodsIds");
		String[] ids = goodsIds.split(",");
		Consumer consumer = consumerService.get(goodsConsumer.getConsumer().getId());
		String goodsids = goodsConsumerService.findGoodIDs(goodsConsumer.getConsumer().getId());
		if (ids != null) {
			for (String id : ids) {
				Goods g = goodsService.get(NumberUtils.toLong(id));
				GoodsConsumer gc = goodsConsumerService.getGoodsConcumerByGIDandCID(NumberUtils.toLong(id), consumer.getId());
				if (gc.getId() == null && g != null) {
					gc.setGoods(g);
					gc.setSell_price(NumberUtils.toDouble(g.getSellPrice()));
					gc.setSpreadprice(g.getSpreadprice());
					gc.setConsumer(consumer);
					goodsConsumerService.save(gc);
				}
				if (goodsids.indexOf("," + id) >= 0) {
					goodsids = goodsids.replace(id, "");
				}
			}
		}
		List<GoodsConsumer> list = goodsConsumerService.findLists(goodsConsumer.getConsumer().getId());
		for (GoodsConsumer c : list) {
			if (c.getGoods() == null || goodsids.indexOf("," + c.getGoods().getId()) >= 0) {
				goodsConsumerService.delete(c.getId());
			}
		}
		return "redirect:" + Global.getAdminPath() + "/bussiness/goodsConsumer/list?consumer.id=" + goodsConsumer.getConsumer().getId();
	}

	@RequiresPermissions("bussiness:goodsConsumer:edit")
	@RequestMapping(value = { "list", "" })
	public String list(GoodsConsumer goodsConsumer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Consumer consumer = consumerService.get(goodsConsumer.getConsumer().getId());
		model.addAttribute("consumer", consumer);
		Page<GoodsConsumer> page = goodsConsumerService.find(new Page<GoodsConsumer>(request, response), goodsConsumer);
		model.addAttribute("page", page);
		return "modules/bussiness/goodsConsumerList";
	}

	@RequiresPermissions("bussiness:goodsConsumer:edit")
	@RequestMapping(value = "form")
	public String form(GoodsConsumer goodsConsumer, Model model) {
		model.addAttribute("goodsConsumer", goodsConsumer);
		return "modules/bussiness/goodsConsumerForm";
	}

	@RequiresPermissions("bussiness:goodsConsumer:edit")
	@RequestMapping(value = "save")
	public String save(Long gcid, GoodsConsumerVo vo, Model model, GoodsConsumer goodsConsumer, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("goodsConsumer", goodsConsumer);
		for (GoodsConsumer g : vo.getGoodsConsumerlist()) {
			if (g != null && g.getId() != null) {
				GoodsConsumer s = goodsConsumerService.get(g.getId());
				s.setSell_price(g.getSell_price());
				s.setSpreadprice(s.getSell_price() - NumberUtils.toDouble(s.getGoods().getPurchasePrice()));
				goodsConsumerService.save(s);
			}
		}
		addMessage(redirectAttributes, "保存销售价成功!");
		return "redirect:" + Global.getAdminPath() + "/bussiness/goodsConsumer?repage&consumer.id=" + gcid;
	}

	@RequiresPermissions("bussiness:goodsConsumer:edit")
	@RequestMapping(value = "deleteall")
	public String deleteall(Long gcid, Long id, RedirectAttributes redirectAttributes) {
		try {
			goodsConsumerService.deleteByConsumerId(gcid);
			addMessage(redirectAttributes, "删除成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "删除失败");
		}
		return "redirect:" + Global.getAdminPath() + "/bussiness/goodsConsumer?repage&consumer.id=" + gcid;
	}

	@RequiresPermissions("bussiness:goodsConsumer:edit")
	@RequestMapping(value = "delete")
	public String delete(Long gcid, Long id, RedirectAttributes redirectAttributes) {
		try {
			goodsConsumerService.delete(id);
			addMessage(redirectAttributes, "删除成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "删除失败");
		}
		return "redirect:" + Global.getAdminPath() + "/bussiness/goodsConsumer?repage&consumer.id=" + gcid;
	}

}
