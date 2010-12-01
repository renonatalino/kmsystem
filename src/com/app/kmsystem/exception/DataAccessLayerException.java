package com.app.kmsystem.exception;

/**
 * File: com.app.kmsystem.exception.DataAccessLayerException.java
 * <p/>
 * Project: kmsystem
 * Author : Reno Natalino
 * Created: Nov 27, 2010, 11:02:12 AM
 */

public class DataAccessLayerException extends RuntimeException{
    public DataAccessLayerException(String message){
        super(message);
    }

    public DataAccessLayerException(String message, Throwable couse){
        super(message, couse);
        initCause(couse);
    }
}
