package com.example.transport_service.exception;

public class TransportNotFoundByIdException extends Exception{
    public TransportNotFoundByIdException(String message) {
        super(message);
    }
}
