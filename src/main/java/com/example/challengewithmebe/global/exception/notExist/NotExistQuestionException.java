package com.example.challengewithmebe.global.exception.notExist;

public class NotExistQuestionException extends RuntimeException{
    public NotExistQuestionException(String message){
        super(message);
    }
    public NotExistQuestionException(String message, Throwable cause){
        super(message,cause);
    }
    public NotExistQuestionException() {}
}
