package com.ecommerce_platform.infra.exception;

import com.ecommerce_platform.infra.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends BaseException {
    public ProductNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}