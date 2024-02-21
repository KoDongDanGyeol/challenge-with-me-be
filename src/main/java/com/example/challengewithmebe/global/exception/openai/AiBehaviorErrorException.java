package com.example.challengewithmebe.global.exception.openai;

public class AiBehaviorErrorException extends RuntimeException{
    public AiBehaviorErrorException(String message){
        super(message);
    }
    public AiBehaviorErrorException(String message, Throwable cause){
        super(message,cause);
    }
    public AiBehaviorErrorException() {}
}
