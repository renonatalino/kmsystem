package com.app.kmsystem.exception;

/**
 * File: com.app.kmsystem.exception.SystemException.java
 * <p/>
 * Project: kmsystem
 * Author : Reno Natalino
 * Created: Nov 27, 2010, 11:03:06 AM
 */

public class SystemException extends RuntimeException{
    public SystemException(String message){
        super(message);
    }

    public SystemException(String message, Throwable couse){
        super(message, couse);
        initCause(couse);
    }
}
