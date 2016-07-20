package com.founder.tools;

import java.text.SimpleDateFormat;
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
}
