package com.bootcamp.firstcheckout.exceptions.base;

public class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }
}