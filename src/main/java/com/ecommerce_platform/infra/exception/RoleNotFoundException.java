package com.ecommerce_platform.infra.exception;

import com.ecommerce_platform.infra.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends BaseException {
    public RoleNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
