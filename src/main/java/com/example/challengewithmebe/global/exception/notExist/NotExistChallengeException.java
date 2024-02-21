package com.example.challengewithmebe.global.exception.notExist;

public class NotExistChallengeException extends RuntimeException{
    public NotExistChallengeException(String message){
        super(message);
    }
    public NotExistChallengeException(String message, Throwable cause){
        super(message,cause);
    }
    public NotExistChallengeException() {}
}
