package com.ecommerce_platform.infra.exception;

import com.ecommerce_platform.infra.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}