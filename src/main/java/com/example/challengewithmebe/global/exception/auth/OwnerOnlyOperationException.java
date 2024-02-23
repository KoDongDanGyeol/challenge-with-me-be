package com.example.challengewithmebe.global.exception.auth;

public class OwnerOnlyOperationException extends RuntimeException{
    public OwnerOnlyOperationException(String message){
        super(message);
    }
    public OwnerOnlyOperationException(String message, Throwable cause){
        super(message,cause);
    }
    public OwnerOnlyOperationException() {}
}
