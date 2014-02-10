/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.dangpu.web;

import java.text.DecimalFormat;
import java.util.Date;

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
import com.whty.platform.modules.dangpu.entity.Ticket;
import com.whty.platform.modules.dangpu.service.TicketService;
import com.whty.platform.modules.sys.entity.User;
import com.whty.platform.modules.sys.utils.UserUtils;

/**
 * 当票管理Controller
 * 
 * @author wanghuaxing
 * @version 2013-12-30
 */
@Controller
@RequestMapping(value = "${adminPath}/dangpu/ticket")
public class TicketController extends BaseController {
	private static final double res = 0.2; 
	
	@Autowired
	private TicketService ticketService;

	@ModelAttribute
	public Ticket get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return ticketService.get(id);
		} else {
			Ticket ticket = new Ticket();
			ticket.setStartTime(new Date());
			return new Ticket();
		}
	}

	@RequestMapping(value = { "list", "" })
	public String list(Ticket ticket, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Ticket> page = ticketService.find(new Page<Ticket>(request, response), ticket);
		model.addAttribute("page", page);
		return "modules/dangpu/ticketList";
	}

	@RequestMapping(value = "form")
	public String form(Ticket ticket, Model model) {
		model.addAttribute("ticket", ticket);
		return "modules/dangpu/ticketForm";
	}


	@RequestMapping(value = "save")
	public String save(Ticket ticket, Model model, RedirectAttributes redirectAttributes) {
		ticket.setUser(UserUtils.getUser());
		ticket.setRedeemUser(UserUtils.getUser());
		ticket.setCreateStartTime(new Date());
		ticket.setStatus(ticket.PAWN_STATUS_TICKET);
		ticket.setInterest(ticket.getInterest()/100);
		ticketService.save(ticket);
		addMessage(redirectAttributes, "录入当票成功");
		return "redirect:" + Global.getAdminPath() + "/dangpu/ticket/?repage";
	}
	
	@RequestMapping(value = "redeemForm")
	public String redeemForm(Ticket ticket, Model model) {
		model.addAttribute("ticket", ticket);
		return "modules/dangpu/ticketRedeem";
	}
	
	@RequestMapping(value = "detail")
	public String detail(Ticket ticket, Model model) {
		model.addAttribute("ticket", ticket);
		return "modules/dangpu/ticketInfo";
	}
	
	@RequestMapping(value = "redeem")
	public String redeem(Ticket ticket, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ticket)) {
			return form(ticket, model);
		}
		ticket.setUser(UserUtils.getUser());
		
		double acount = ticketService.acount(ticket.getMoney(),ticket.getInterest(), ticket.getStartTime(), ticket.getEndTime());
		ticket.setProfit(acount);
		DecimalFormat df = new DecimalFormat("#");
		ticket.setBadDebt(Double.parseDouble(df.format(acount*res)));
		ticket.setCreateEndTime(new Date());
		ticket.setStatus(ticket.PAWN_STATUS_REDEEM);
		ticketService.save(ticket);
		addMessage(redirectAttributes, "赎当成功");
		return "redirect:" + Global.getAdminPath() + "/dangpu/ticket/?repage";
	}

	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		ticketService.delete(id);
		addMessage(redirectAttributes, "删除当票成功");
		return "redirect:" + Global.getAdminPath() + "/dangpu/ticket/?repage";
	}

}
