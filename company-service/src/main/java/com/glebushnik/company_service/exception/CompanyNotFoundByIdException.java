package com.glebushnik.company_service.exception;

public class CompanyNotFoundByIdException extends Exception {
    public CompanyNotFoundByIdException(String message) {
        super(message);
    }
}
