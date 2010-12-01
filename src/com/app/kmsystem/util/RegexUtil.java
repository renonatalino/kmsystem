package com.app.kmsystem.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * File: com.app.kmsystem.util
 * 
 * Project:	kmsystem
 * Author : Reno Natalino
 * Created: Nov 15, 2010, 4:47:45 PM
 */

public abstract class RegexUtil {
    private static final Log log = LogFactory.getLog(RegexUtil.class);

    public interface EXP{
        String EMAIL_REGEX = "([0-9a-zA-Z]+[-._+&amp;])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}";
        String NUMBER_REGEX = "^([0-9])*$";
		String ZERO_REGEX = "^[0]([0])*$";
    }

    public static boolean validateStr(String valueString, String Regex){
        boolean result = false;
        if(!StringUtils.isNullorEmptyString(valueString)){
            try {
                Pattern p = Pattern.compile(valueString);
                Matcher m = p.matcher(valueString);
                result = m.matches();
            } catch (Exception e) {
                log.error("Fail to validate regex : " + e);    
            }
        }
        return result;
    }
}
