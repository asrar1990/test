package com.bn.account.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class AppCalendar 
{
	  private AppCalendar(){}
	  private static int mDeltaTime;
	  public static synchronized void setDeltaTime(int delta)
	  {
		  mDeltaTime=delta;
	  }
	  
	  public static Calendar getCalendar()
	  {
		  Calendar cal = GregorianCalendar.getInstance();
		  cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		  cal.add(Calendar.MINUTE, mDeltaTime);
		  return cal;
	  }
	  
	  public static void  main(String args[])
	  {
		  System.out.println(AppCalendar.getCalendar().getTime()); //main
		  AppCalendar.setDeltaTime(1400*365*10);
		  System.out.println(AppCalendar.getCalendar().getTime()); //main
	  }
}
