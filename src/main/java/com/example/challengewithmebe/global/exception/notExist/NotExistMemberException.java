package com.example.challengewithmebe.global.exception.notExist;

public class NotExistMemberException extends RuntimeException{
    public NotExistMemberException(String message){
        super(message);
    }
    public NotExistMemberException(String message, Throwable cause){
        super(message,cause);
    }
    public NotExistMemberException() {}
}
