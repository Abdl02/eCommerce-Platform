package com.ecommerce_platform.service;

import com.ecommerce_platform.repository.entity.Order;
import com.ecommerce_platform.repository.entity.OrderStatus;
import com.ecommerce_platform.repository.entity.Payment;
import com.ecommerce_platform.infra.exception.OrderNotFoundException;
import com.ecommerce_platform.infra.exception.PaymentFailedException;
import com.ecommerce_platform.repository.repos.OrderRepository;
import com.ecommerce_platform.repository.repos.PaymentRepository;
import com.ecommerce_platform.infra.util.EmailUtil;
import com.ecommerce_platform.infra.util.LoggerUtil;
import com.ecommerce_platform.infra.util.OrderUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final EmailUtil emailUtil;
    private final OrderUtil orderUtil;

    @Transactional
    public String processPayment(Long orderId, BigDecimal amount, String paymentMethod) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found"));

        orderUtil.validateOrderHasUser(order);

        validatePaymentAmount(order, amount);

        Payment payment = new Payment(null, order, amount, LocalDateTime.now(), paymentMethod);
        paymentRepository.save(payment);

        orderUtil.updateOrderStatus(order, OrderStatus.CONFIRMED);
        orderRepository.save(order);

        sendPaymentConfirmation(order);
        return "Payment processed successfully for order ID: " + orderId;
    }

    private void validatePaymentAmount(Order order, BigDecimal amount) {
        if (amount.compareTo(order.getTotalAmount()) < 0) {
            throw new PaymentFailedException("Payment amount " + amount + " is insufficient for order total " + order.getTotalAmount());
        }
    }

    private void sendPaymentConfirmation(Order order) {
        try {
            emailUtil.sendPaymentConfirmation(
                    order.getUser().getEmail(),
                    "Payment Confirmation",
                    "Your payment for order ID " + order.getId() + " has been successfully processed."
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to send payment confirmation email", e);
        }
    }
}