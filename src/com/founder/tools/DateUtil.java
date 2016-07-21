package com.founder.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String getNowDate(){
		return getNowDate("yyyyMMdd");
	}

	public static String getNowDate(String pattern){
		SimpleDateFormat sf=new SimpleDateFormat(pattern);
		return sf.format(new Date());
	}

	public static String getNowTime(){
		return getNowDate("HH:mm:ss");
	}

	public static String getNowTime(String pattern){
		SimpleDateFormat sf=new SimpleDateFormat(pattern);
		return sf.format(new Date());
	}
	
	public static String getNow(){
		return getNowDate("yyyyMMdd HH:mm:ss");
	}

	public static String getNow(String pattern){
		SimpleDateFormat sf=new SimpleDateFormat(pattern);
		return sf.format(new Date());
	}
	
	public static Date parse(String source,String pattern){
		SimpleDateFormat sf=new SimpleDateFormat(pattern);
		try {
			return sf.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解析"yyyyMMdd"样式的日期
	 */
	public static Date parse(String source){
		return parse(source,"yyyyMMdd");
	}
	
	/**
	 * 两个日期的比较，只有日期的比较
	 */
	public static int compare(Date d1,Date d2){
		Calendar car = Calendar.getInstance();
		car.setTime(d1);
		
		int day1 = car.get(Calendar.DAY_OF_YEAR);
		int year1 = car.get(Calendar.YEAR);
		car.setTime(d2);
		int day2 = car.get(Calendar.DAY_OF_YEAR);
		int year2 = car.get(Calendar.YEAR);
		
		if(year1==year2){
			return Math.abs(day1 - day2);
		} else {	//此处虽然区分闰年，但不能保证是否包含了2月
			int yyss = 0;
			for(int i=year2;i<year1;i++){
				if((i%4==0&&i%100!=0)||i%400==0){
					yyss+=366;
				}else{
					yyss+=365;
				}
			}
			return Math.abs(day1 - day2)+Math.abs(yyss);
		}
	}
	
	/**
	 * 工作日的比较
	 */
	public static int compareWorkday(Date d1,Date d2){
		Calendar car = Calendar.getInstance();
		car.setTime(d1);
		int day1 = car.get(Calendar.DAY_OF_YEAR);
		car.setTime(d2);
		int day2 = car.get(Calendar.DAY_OF_YEAR);
		return day1 - day2;
	}
}
