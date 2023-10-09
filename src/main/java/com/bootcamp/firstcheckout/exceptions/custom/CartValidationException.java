package com.bootcamp.firstcheckout.exceptions.custom;

import com.bootcamp.firstcheckout.exceptions.base.ValidationException;

public class CartValidationException extends ValidationException {
    public Integer code;
    public CartValidationException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}