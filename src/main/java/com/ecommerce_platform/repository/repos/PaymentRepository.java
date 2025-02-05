package com.ecommerce_platform.repository.repos;

import com.ecommerce_platform.repository.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}