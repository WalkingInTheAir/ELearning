package com.core.util.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	private final static String TIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
	private final static String DATEFORMAT = "yyyy-MM-dd";
	
	public static String getTime(){
		SimpleDateFormat sf = new SimpleDateFormat(TIMEFORMAT);
		return sf.format(new Date());
	}
	
	public static String getDate(){
		SimpleDateFormat sf = new SimpleDateFormat(DATEFORMAT);
		return sf.format(new Date());
	}
	
	public static long getTimeMills(){
		return System.currentTimeMillis();
	}
	public static void main(String[] args) {
		System.out.println(DateUtil.getTime() + ", " + DateUtil.getDate() + ", " + getTimeMills());
	}

}
