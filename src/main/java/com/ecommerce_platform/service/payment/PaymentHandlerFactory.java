package com.ecommerce_platform.service.payment;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Factory class to select the appropriate payment handler dynamically
 * based on the provided payment method.
 * <p>
 * Design Pattern:
 * - Implements Factory Design Pattern to encapsulate payment handler selection,
 *   promoting Open/Closed Principle (easily add new handlers without modifying existing code).
 */
@Component
public class PaymentHandlerFactory {

    private final Map<String, PaymentHandler> handlerMap;

    public PaymentHandlerFactory(Map<String, PaymentHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    /**
     * Retrieves the appropriate payment handler based on the provided payment method.
     *
     * @param paymentMethod the payment method (e.g., CREDIT_CARD, PAYPAL)
     * @return the corresponding payment handler
     * @throws IllegalArgumentException if the payment method is unsupported
     */
    public PaymentHandler getHandler(String paymentMethod) {
        PaymentHandler handler = handlerMap.get(paymentMethod.toUpperCase());
        if (handler == null) {
            throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        }
        return handler;
    }
}