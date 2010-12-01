package com.app.kmsystem.exception;

/**
 * File: com.app.kmsystem.exception.ApplicationException.java
 * <p/>
 * Project: kmsystem
 * Author : Reno Natalino
 * Created: Nov 27, 2010, 11:01:19 AM
 */

public class ApplicationException extends RuntimeException{
    public ApplicationException(String message){
        super(message);
    }

    public ApplicationException(String message, Throwable couse){
        super(message, couse);
        initCause(couse);
    }
}
