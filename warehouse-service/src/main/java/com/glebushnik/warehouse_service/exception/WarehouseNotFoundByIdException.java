package com.glebushnik.warehouse_service.exception;

public class WarehouseNotFoundByIdException extends Exception {
    public WarehouseNotFoundByIdException(String message) {
        super(message);
    }
}
