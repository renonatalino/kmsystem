package com.app.kmsystem.util;

import flexjson.DateTransformer;
import flexjson.JSONSerializer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * File: com.app.kmsystem.util.JSONUtil.java
 * <p/>
 * Project: kmsystem
 * Author : Reno Natalino
 * Created: Nov 25, 2010, 2:02:13 PM
 */

public class JSONUtil {
    private Map<String, Object> data;
    private Set<String> dateFields;
    private String dateFormat;

    public JSONUtil() {
        data = new HashMap<String, Object>();
    }

    public void addData(String key, Object value){
        data.put(key, value);
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void addDateFields(String fieldNames) {
        if(dateFields == null){dateFields = new HashSet<String>();}
        dateFields.add(fieldNames);
    }

    public void setDateFields(Set<String> dateFields) {
        this.dateFields = dateFields;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String serialize(){
        JSONSerializer json = new JSONSerializer();
        if((dateFields != null) && (dateFields.size() > 0)){
            DateTransformer transformer = new DateTransformer(CommonConstants.FORMAT_DATE);
            if(null != this.getDateFormat()){transformer = new DateTransformer(this.getDateFormat());}
            for (String field : dateFields){json.transform(transformer, field);}
        }
        return json.exclude("*.class").deepSerialize(data);
    }
}
