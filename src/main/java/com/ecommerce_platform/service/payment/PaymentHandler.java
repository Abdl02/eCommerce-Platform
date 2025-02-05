package com.ecommerce_platform.service.payment;

import com.ecommerce_platform.api.dto.request.PaymentRequest;
import com.ecommerce_platform.repository.entity.Payment;

/**
 * Interface defining the contract for payment processing.
 */
public interface PaymentHandler {
    void processPayment(PaymentRequest request, Payment payment);
}