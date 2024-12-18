package com.example.transport_service.exception;

public class UserNotFoundByIdException extends Exception{
    public UserNotFoundByIdException(String message) {
        super(message);
    }
}
