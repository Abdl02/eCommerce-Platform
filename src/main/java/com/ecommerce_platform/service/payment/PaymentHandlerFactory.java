package com.ecommerce_platform.service.payment;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Factory to dynamically select the appropriate payment handler
 * based on the given payment method.
 */
@Component
public class PaymentHandlerFactory {

    private final Map<String, PaymentHandler> handlerMap;

    public PaymentHandlerFactory(Map<String, PaymentHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public PaymentHandler getHandler(String paymentMethod) {
        PaymentHandler handler = handlerMap.get(paymentMethod.toUpperCase());
        if (handler == null) {
            throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        }
        return handler;
    }
}