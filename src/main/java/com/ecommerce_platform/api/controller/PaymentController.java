package com.ecommerce_platform.api.controller;

import com.ecommerce_platform.api.dto.request.PaymentRequest;
import com.ecommerce_platform.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest request) {
        String message = paymentService.processPayment(request.orderId(), request.amount(), request.paymentMethod());
        return ResponseEntity.ok(message);
    }
}