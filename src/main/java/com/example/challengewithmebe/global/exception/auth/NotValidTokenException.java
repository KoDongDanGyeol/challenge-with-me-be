package com.example.challengewithmebe.global.exception.auth;

public class NotValidTokenException extends RuntimeException{
    public NotValidTokenException(String message){
        super(message);
    }
    public NotValidTokenException(String message, Throwable cause){
        super(message,cause);
    }
    public NotValidTokenException() {}
}
