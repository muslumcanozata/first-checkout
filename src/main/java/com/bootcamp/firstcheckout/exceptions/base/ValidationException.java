package com.bootcamp.firstcheckout.exceptions.base;

public class ValidationException extends ApplicationException {
    public ValidationException(String message) {
        super(message);
    }
}