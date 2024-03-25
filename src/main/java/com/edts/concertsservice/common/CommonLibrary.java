package com.edts.concertsservice.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class CommonLibrary {

	// Response Code
	public static final int SUCCESS_CODE = 1000;
	public static final int FAILED_CODE = 1005;
	public static final int NOT_FOUND_CODE = 1004;
	public static final int INTERNAL_SERVER_ERROR_CODE = 5000;
	
	// Response Message
	public static final String SUCCESS_MESSAGE = "Success";
	public static final String FAILED_MESSAGE = "Failed";
	public static final String NOT_FOUND_MESSAGE = "Data not found";
	public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server Error";
	
	public static final String DATA_ALREADY_EXIST = "Data already exist";
	public static final String DUPLICATE_IS_NOT_ALLOWED = "Duplicate is not allowed";
	
	public Date formatDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date newDate = null;
		
		try {
			newDate = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return newDate;
	}
	
	public Date stringToDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
	}
	
	public String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		
		return dateStr;
	}
	
	public String mixString(String str) {
		Random rand = new Random();
		
	    int n = rand.nextInt(10);

	    if(n == 1)
	    	return str;
	    
	    char[] str1 = str.toCharArray();
	    int len = str.length();
	    
	    String[] arr = new String[n];
	    Arrays.fill(arr, "");

	    int row = 0;
	    boolean down = true;

	    for(int i = 0; i < len; i++) {
	    	arr[row] += (str1[i]);
	    	
	    	if(row == n - 1) {
	    		down = false;
	    	} else if(row == 0) {
	    		down = true;
	    	}
	    	
	    	if(down) {
	    		row++;
	    	} else {
	    		row--;
	    	}
	    }

	    String rest = "";

	    for(int i = 0; i < n; i++) {
	    	rest = rest + arr[i];
	    }
	    
		return rest;
	}
}
