/**
 * There are <a href="http://www.whty.com.cn">whty</a> code generation
 */
package com.whty.platform.modules.dangpu.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.whty.platform.common.persistence.Page;
import com.whty.platform.common.service.BaseService;
import com.whty.platform.common.utils.DateUtils;
import com.whty.platform.modules.bussiness.entity.Consumer;
import com.whty.platform.modules.dangpu.dao.TicketDao;
import com.whty.platform.modules.dangpu.entity.Ticket;

/**
 * 当票管理Service
 * 
 * @author qimin
 * @version 2013-07-10
 */
@Component
@Transactional(readOnly = true)
public class TicketService extends BaseService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(TicketService.class);
	
	private static final double res = 0.01;

	@Autowired
	private TicketDao ticketDao;

	public Ticket get(Long id) {
		return ticketDao.findOne(id);
	}

	public Page<Ticket> find(Page<Ticket> page, Ticket ticket) {
		DetachedCriteria dc = ticketDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(ticket.getName())) {
			dc.add(Restrictions.like("name", ticket.getName(), MatchMode.ANYWHERE));
		}
		
		if (StringUtils.isNotEmpty(ticket.getStatus())) {
			dc.add(Restrictions.eq("status", ticket.getStatus()));
		}
		
		
		//录入人员
		if (ticket.getUser() != null && StringUtils.isNotBlank(ticket.getUser().getName())) {
			dc.createAlias("user", "user");
			dc.add(Restrictions.like("user.name", ticket.getUser().getName(), MatchMode.ANYWHERE));
		}
		
		//时间
		if(ticket.getSearchStartTime()!=null&&ticket.getSearchEndTime()!=null){
			dc.add(Restrictions.between("startTime", ticket.getSearchStartTime(), ticket.getSearchEndTime()));
		}
		
		dc.add(Restrictions.eq(Ticket.DEL_FLAG, Ticket.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return ticketDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void save(Ticket ticket) {
		ticketDao.save(ticket);
		ticketDao.flush();
		ticketDao.clear();
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		ticketDao.deleteById(id);
	}

	public List<Consumer> findALL() {
		return ticketDao.findALL();
	}
	
	
	
	/**
	 * 计算利息
	 * 
	 * @param money
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public double acount(double money,double res, Date startTime, Date endTime) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String startTimet = f.format(startTime);
		String endTimet = f.format(endTime);
		long month = getMonth(startTimet, endTimet);
		long day = getDay(startTimet, endTimet);
		long days = getDays(startTimet, endTimet);
		double moneyAcount = money*res*month+money*res/days*day;
		DecimalFormat df = new DecimalFormat("#");
		
		return Double.parseDouble(df.format(moneyAcount/100))*100;
	}

	
	/**
     * 得到两日期相差几个月
     * 
     * @param String
     * @return
     */
    private long getMonth(String startDate, String endDate) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        long monthday;
        try {
            Date startDate1 = f.parse(startDate);
            Date endDate1 = f.parse(endDate);

            Calendar starCal = Calendar.getInstance();
            starCal.setTime(startDate1);

            int sYear = starCal.get(Calendar.YEAR);
            int sMonth = starCal.get(Calendar.MONTH);
            int sDay = starCal.get(Calendar.DATE);

            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endDate1);
            int eYear = endCal.get(Calendar.YEAR);
            int eMonth = endCal.get(Calendar.MONTH);
            int eDay = endCal.get(Calendar.DATE);

            monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));
            
            if (sDay > eDay) {
            	monthday = monthday - 1;
            }
            
            System.out.println("monthday is {"+monthday+"}");

            return monthday;
        } catch (ParseException e) {
            System.out.println("获取相差月数失败");
            monthday = 0;
        }
        return monthday;
    }
    
    /**
     * 得到两日期相差多少天
     * 
     * @param String
     * @return
     */
    private long getDay(String startDate, String endDate) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        long day = -1;
        try {
            //开始日期
            Calendar starCal = Calendar.getInstance();
            Date startDate1 = f.parse(startDate);
            starCal.setTime(startDate1);
            int sDay = starCal.get(Calendar.DATE);
            
            //结束日期
            Calendar endCal = Calendar.getInstance();
            Date endDate1 = f.parse(endDate);
            endCal.setTime(endDate1);
            int eDay = endCal.get(Calendar.DATE);

            if (sDay <= eDay) {
            	day = eDay - sDay;
            }else{
            	Calendar lastCal = Calendar.getInstance();
            	endCal.add(Calendar.MONTH, -1);
            	
            	int year = endCal.get(Calendar.YEAR);
            	int month = endCal.get(Calendar.MONTH);
            	int dayMonth = starCal.get(Calendar.DAY_OF_MONTH);
            	lastCal.set(year, month, dayMonth);
            	Date lastDate = lastCal.getTime();
            	String lastDateStr = f.format(lastDate).toString();
            	//System.out.println("lastDate is {"+lastDateStr+"}");
            	
            	day = getDateDifferent(lastDateStr, endDate);
            }
            
            
            System.out.println("day is {"+day+"}");

            return day;
        } catch (ParseException e) {
            System.out.println("获取相差月数失败");
            day = 0;
        }
        return day;
    }
    
    /**
     * 得到两日期相差多少天
     * 
     * @param String
     * @return
     */
    private long getDays(String startDate, String endDate) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        long day = -1;
        try {
            //开始日期
            Calendar starCal = Calendar.getInstance();
            Date startDate1 = f.parse(startDate);
            starCal.setTime(startDate1);
            int sDay = starCal.get(Calendar.DATE);
            
            //结束日期
            Calendar endCal = Calendar.getInstance();
            Date endDate1 = f.parse(endDate);
            endCal.setTime(endDate1);
            int eDay = endCal.get(Calendar.DATE);
            
            if (sDay < eDay) {
            	Calendar lastCal = Calendar.getInstance();
            	endCal.add(Calendar.MONTH, 1);
            	
            	int year = endCal.get(Calendar.YEAR);
            	int month = endCal.get(Calendar.MONTH);
            	int dayMonth = endCal.get(Calendar.DAY_OF_MONTH);
            	lastCal.set(year, month, dayMonth);
            	Date lastDate = lastCal.getTime();
            	String lastDateStr = f.format(lastDate).toString();
            	System.out.println("lastDate is {"+lastDateStr+"}");
            	// System.out.println("天数：" + endCal.getActualMaximum(Calendar.DAY_OF_MONTH));  
            	
            	day = getDateDifferent(endDate,lastDateStr);
            }else{
            	Calendar lastCal = Calendar.getInstance();
            	endCal.add(Calendar.MONTH, -1);
            	
            	int year = endCal.get(Calendar.YEAR);
            	int month = endCal.get(Calendar.MONTH);
            	int dayMonth = endCal.get(Calendar.DAY_OF_MONTH);
            	lastCal.set(year, month, dayMonth);
            	Date lastDate = lastCal.getTime();
            	String lastDateStr = f.format(lastDate).toString();
            	System.out.println("lastDate is {"+lastDateStr+"}");
            	
            	day = getDateDifferent(lastDateStr, endDate);
            }
            
            
            System.out.println("days is {"+day+"}");

            return day;
        } catch (ParseException e) {
            System.out.println("获取相差月数失败");
            day = 0;
        }
        return day;
    }
    
    /**
     * 两个时间相隔多少天
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    private long getDateDifferent(String startDate, String endDate){
    	SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
    	long diffDays=-1;
		try {
			Date startDate1 = f.parse(startDate);
			Date endDate1 = f.parse(endDate);
			
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(startDate1);
			//calendar1.set(2007, 01, 10);
			long milliseconds1 = calendar1.getTimeInMillis();
			
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(endDate1);
			//calendar2.set(2007, 07, 01);
			long milliseconds2 = calendar2.getTimeInMillis();
			
			
			long diff = milliseconds2 - milliseconds1;
			long diffSeconds = diff / 1000;
			long diffMinutes = diff / (60 * 1000);
			long diffHours = diff / (60 * 60 * 1000);
			diffDays = diff / (24 * 60 * 60 * 1000);
//			System.out.println("\nThe Date Different Example");
//			System.out.println("Time in milliseconds: " + diff + " milliseconds.");
//			System.out.println("Time in seconds: " + diffSeconds + " seconds.");
//			System.out.println("Time in minutes: " + diffMinutes + " minutes.");
//			System.out.println("Time in hours: " + diffHours + " hours.");
//			System.out.println("Time in days: " + diffDays + " days.");
		} catch (ParseException e) {
			System.out.println("获取相差天数失败:"+e);
		}
        
        return diffDays;
    }

}
