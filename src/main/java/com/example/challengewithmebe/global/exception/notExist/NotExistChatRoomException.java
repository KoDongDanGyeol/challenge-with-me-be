package com.example.challengewithmebe.global.exception.notExist;

public class NotExistChatRoomException extends RuntimeException{
    public NotExistChatRoomException(String message){
        super(message);
    }
    public NotExistChatRoomException(String message, Throwable cause){
        super(message,cause);
    }
    public NotExistChatRoomException() {}
}
