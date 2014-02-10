package com.whty.platform.modules.luojia.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateUtils {
	public final static Date toDate(String date)throws Exception{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sf.parse(date);
	}
	
	public final static String getDateStringYMD()throws Exception{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(new Date());
	}
	
	public final static String getDateStringYMD(Date d)throws Exception{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(d);
	}
	
	public final static String getDateStringYMdHM(Date d)throws Exception{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sf.format(d);
	}
	
	public final static String addDate(int d)throws Exception{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		gc.add(5,d);
		return sf.format(gc.getTime());
	}
	
	public final static String addDateYMd(Date date,int d)throws Exception{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(5,d);
		return sf.format(gc.getTime());
	}
	
	public final static String addDateYMdHM(Date date,int d)throws Exception{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(5,d);
		return sf.format(gc.getTime());
	}
	
	public static void main(String[] args){
		try {
			String dptTime = "0823";
			System.out.println(dptTime.substring(0,2) + ":" + dptTime.substring(2,dptTime.length()));
			System.out.println(addDate(10));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
