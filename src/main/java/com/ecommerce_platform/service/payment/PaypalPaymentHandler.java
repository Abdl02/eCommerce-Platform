package com.ecommerce_platform.service.payment;

import com.ecommerce_platform.api.dto.request.PaymentRequest;
import com.ecommerce_platform.repository.entity.Payment;
import com.ecommerce_platform.infra.util.LoggerUtil;
import org.springframework.stereotype.Component;

@Component
public class PaypalPaymentHandler implements PaymentHandler {

    @Override
    public void processPayment(PaymentRequest request, Payment payment) {
        LoggerUtil.logAction("Processing PayPal payment of " + request.amount());
        payment.setPaymentMethod("PAYPAL");
    }
}