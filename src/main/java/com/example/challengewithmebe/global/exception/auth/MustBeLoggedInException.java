package com.example.challengewithmebe.global.exception.auth;

public class MustBeLoggedInException extends RuntimeException{
    public MustBeLoggedInException(String message){
        super(message);
    }
    public MustBeLoggedInException(String message, Throwable cause){
        super(message,cause);
    }
    public MustBeLoggedInException() {}
}
