package com.example.myapplication.framework.retrofit.services;

public class AWSException extends Exception {

    public AWSException(String message, Throwable cause) {
        super(message, cause);
    }

    public AWSException(String message) {
        super(message);
    }
}
