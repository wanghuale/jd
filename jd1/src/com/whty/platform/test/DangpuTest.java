package com.whty.platform.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DangpuTest {

	private static final double res = 0.03;

	public static void main(String args[]) {
		//String startTime = "2013-12-04";
		String startTime = "2013-09-04";
		String endTime = "2014-01-17";
		long money = 200000;
		
		//getLastMonth();36000
		//getMonth(startTime, endTime);
		//getDay(startTime, endTime);
		//getDateDifferent(startTime, endTime);
		
		acount(money, startTime, endTime);
	}
	
	/**
	 * 计算利息
	 * 
	 * @param money
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private static double acount(long money, String startTime, String endTime) {
		long month = getMonth(startTime, endTime);
		long day = getDay(startTime, endTime);
		double moneyAcount = money*res*month+money*res/30*day;
		
		System.out.println("moneyAccount is {"+moneyAcount+"}");
		return moneyAcount;
	}

	/**
	 * 计算月份
	 * 
	 * @return
	 */
	private static Date getLastMonth() {
		Calendar c = Calendar.getInstance();
		// 得到一个月最后一天日期(31/30/29/28)
		int MaxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 按你的要求设置时间
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), MaxDay, 23, 59, 59);
		// 按格式输出
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		String gtime = sdf.format(c.getTime()); // 上月最后一天
		System.out.println(gtime);
		return null;
	}
	
	/**
     * 得到两日期相差几个月
     * 
     * @param String
     * @return
     */
    private static long getMonth(String startDate, String endDate) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        long monthday;
        try {
            Date startDate1 = f.parse(startDate);
            Date endDate1 = f.parse(endDate);

            Calendar starCal = Calendar.getInstance();
            starCal.setTime(startDate1);

            int sYear = starCal.get(Calendar.YEAR);
            int sMonth = starCal.get(Calendar.MONTH)+1;
            int sDay = starCal.get(Calendar.DATE);

            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endDate1);
            int eYear = endCal.get(Calendar.YEAR);
            int eMonth = endCal.get(Calendar.MONTH)+1;
            int eDay = endCal.get(Calendar.DATE);

            monthday = ((eYear - sYear) * 12 + (eMonth - sMonth+1));
            
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
    private static long getDay(String startDate, String endDate) {
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
     * 两个时间相隔多少天
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    private static long getDateDifferent(String startDate, String endDate){
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

