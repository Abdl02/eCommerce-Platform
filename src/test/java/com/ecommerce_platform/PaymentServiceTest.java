package com.ecommerce_platform;

import com.ecommerce_platform.infra.exception.OrderNotFoundException;
import com.ecommerce_platform.infra.exception.PaymentFailedException;
import com.ecommerce_platform.repository.entity.Order;
import com.ecommerce_platform.repository.entity.OrderStatus;
import com.ecommerce_platform.repository.entity.Payment;
import com.ecommerce_platform.repository.entity.User;
import com.ecommerce_platform.repository.repos.OrderRepository;
import com.ecommerce_platform.repository.repos.PaymentRepository;
import com.ecommerce_platform.service.PaymentService;
import com.ecommerce_platform.infra.util.EmailUtil;
import com.ecommerce_platform.infra.util.OrderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderUtil orderUtil;

    @Mock
    private EmailUtil emailUtil;

    private Order order;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User(1L, "john_doe", "password", "user@example.com", null);
        order = new Order(1L, user, LocalDateTime.now(), OrderStatus.PENDING, BigDecimal.valueOf(100), null);
    }

    @Test
    void processPayment_Success() throws IOException {
        BigDecimal paymentAmount = BigDecimal.valueOf(100);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(paymentRepository.save(any(Payment.class))).thenReturn(new Payment());

        doNothing().when(orderUtil).validateOrderHasUser(order);
        doNothing().when(emailUtil).sendPaymentConfirmation(anyString(), anyString(), anyString());

        String result = paymentService.processPayment(order.getId(), paymentAmount, "CREDIT_CARD");

        assertEquals("Payment processed successfully for order ID: 1", result);
        verify(orderRepository).save(order);
        verify(paymentRepository).save(any(Payment.class));
        verify(emailUtil).sendPaymentConfirmation(eq(user.getEmail()), anyString(), anyString());
    }

    @Test
    void processPayment_OrderNotFound() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () ->
                paymentService.processPayment(order.getId(), BigDecimal.valueOf(100), "CREDIT_CARD")
        );
    }

    @Test
    void processPayment_InsufficientAmount() {
        BigDecimal insufficientAmount = BigDecimal.valueOf(50);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        doNothing().when(orderUtil).validateOrderHasUser(order);

        assertThrows(PaymentFailedException.class, () ->
                paymentService.processPayment(order.getId(), insufficientAmount, "CREDIT_CARD")
        );
    }
}