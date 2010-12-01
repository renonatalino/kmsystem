package com.app.kmsystem.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * File: com.app.kmsystem.util
 * 
 * Project:	kmsystem
 * Author : Reno Natalino
 * Created: Nov 15, 2010, 2:17:02 PM
 */

public abstract class DateTimeUtil {
    private static final Log log = LogFactory.getLog(DateTimeUtil.class);
    public static final String FORMAT_DATE = "dd/MM/yyyy";
    public static final String FORMAT_TIME = "HH:mm";

    public static String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat(CommonConstants.FORMAT_DATE);
        Date date = new Date();
        return dateFormat.format(date);        
    }

    public static String getCurrentTime(){
        DateFormat timeFormat = new SimpleDateFormat(CommonConstants.FORMAT_TIME);
        Date date = new Date();
        return timeFormat.format(date);
    }

    public static String formatDateToString(Date date){
        String formateedDate = "";
        if(date == null) return formateedDate;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.FORMAT_DATE);
            formateedDate = formatter.format(date);
        } catch (Exception e) {
            log.error("Fail to format date : " + e);
        }
        return formateedDate;
    }
}
