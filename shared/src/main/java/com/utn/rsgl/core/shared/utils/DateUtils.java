package com.utn.rsgl.core.shared.utils;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static Date StringToDate(String strdate){
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(strdate);
		}catch(Exception e){e.getStackTrace();}
		return date;
	}

	public static String DateToString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strdate = sdf.format(date);
		return strdate;
	}
}
