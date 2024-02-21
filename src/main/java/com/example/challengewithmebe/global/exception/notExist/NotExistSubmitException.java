package com.example.challengewithmebe.global.exception.notExist;

public class NotExistSubmitException extends RuntimeException{
    public NotExistSubmitException(String message){
        super(message);
    }
    public NotExistSubmitException(String message, Throwable cause){
        super(message,cause);
    }
    public NotExistSubmitException() {}
}
