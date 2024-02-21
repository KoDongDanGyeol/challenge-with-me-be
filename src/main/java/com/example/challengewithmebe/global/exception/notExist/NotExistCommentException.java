package com.example.challengewithmebe.global.exception.notExist;

public class NotExistCommentException extends RuntimeException{
    public NotExistCommentException(String message){
        super(message);
    }
    public NotExistCommentException(String message, Throwable cause){
        super(message,cause);
    }
    public NotExistCommentException() {}
}
