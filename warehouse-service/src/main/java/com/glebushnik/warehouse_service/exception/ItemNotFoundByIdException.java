package com.glebushnik.warehouse_service.exception;

public class ItemNotFoundByIdException extends Exception {
    public ItemNotFoundByIdException(String message) {
        super(message);
    }
}
