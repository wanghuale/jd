/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.bussiness.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whty.platform.common.base.web.BaseController;
import com.whty.platform.common.config.Global;
import com.whty.platform.common.persistence.Page;
import com.whty.platform.common.utils.DateUtils;
import com.whty.platform.common.utils.excel.ExportExcel;
import com.whty.platform.modules.bussiness.entity.Consumer;
import com.whty.platform.modules.bussiness.entity.OrderRecord;
import com.whty.platform.modules.bussiness.entity.Provider;
import com.whty.platform.modules.bussiness.service.ConsumerService;
import com.whty.platform.modules.bussiness.service.OrderRecordService;
import com.whty.platform.modules.bussiness.service.ProviderService;
import com.whty.platform.modules.front.utils.FrontUserUtils;

/**
 * 订单Controller
 * 
 * @author qimin
 * @version 2013-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/bussiness/orderRecord")
public class OrderRecordController extends BaseController {

	@Autowired
	private OrderRecordService orderRecordService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private ConsumerService consumerService;

	@ModelAttribute
	public OrderRecord get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return orderRecordService.get(id);
		} else {
			return new OrderRecord();
		}
	}

	@RequiresPermissions("bussiness:orderRecord:view")
	@RequestMapping(value = { "list", "" })
	public String list(@RequestParam Map<String, Object> paramMap,OrderRecord orderRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		String did=request.getParameter("did");
		Page<OrderRecord> page = orderRecordService.find(did,new Page<OrderRecord>(request, response), orderRecord,
				paramMap);
		List<Consumer> clist= consumerService.findALL();
		List<Provider> plist=providerService.findALL();
		model.addAttribute("page", page);
		model.addAllAttributes(paramMap);
		model.addAttribute("clist",clist);
		model.addAttribute("plist",plist);
		return "modules/bussiness/orderRecordList";
	}

	@RequiresPermissions("bussiness:orderRecord:view")
	@RequestMapping(value = "form")
	public String form(OrderRecord orderRecord, Model model) {
		model.addAttribute("orderRecord", orderRecord);
		return "modules/bussiness/orderRecordForm";
	}

	@RequiresPermissions("bussiness:orderRecord:edit")
	@RequestMapping(value = "save")
	public String save(OrderRecord orderRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, orderRecord)) {
			return form(orderRecord, model);
		}
		orderRecordService.save(orderRecord);
		return "redirect:" + Global.getAdminPath() + "/bussiness/orderRecord/?repage";
	}

	@RequiresPermissions("bussiness:orderRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		orderRecordService.delete(id);
		addMessage(redirectAttributes, "删除订单成功");
		return "redirect:" + Global.getAdminPath() + "/bussiness/orderRecord/?repage";
	}
	
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(@RequestParam Map<String, Object> paramMap, OrderRecord orderRecord,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		Consumer c = FrontUserUtils.getSession(request);
		orderRecord.setConsumer(c);
		String did=request.getParameter("did");
		Page<OrderRecord> page = orderRecordService.find(did,new Page<OrderRecord>(request, response), orderRecord,
				paramMap);
		try {
			String fileName = "SP平台交易明细" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			new ExportExcel("SP平台交易明细", OrderRecord.class).setDataList(page.getList()).write(response, fileName)
					.dispose();
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导出交易明细失败！失败信息：" + e.getMessage());
		}
		return null;
	}

}
