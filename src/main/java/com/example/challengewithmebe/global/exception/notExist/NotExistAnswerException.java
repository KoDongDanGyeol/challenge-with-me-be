package com.example.challengewithmebe.global.exception.notExist;

public class NotExistAnswerException extends RuntimeException{
    public NotExistAnswerException(String message){
        super(message);
    }
    public NotExistAnswerException(String message, Throwable cause){
        super(message,cause);
    }
    public NotExistAnswerException() {}
}
