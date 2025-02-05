package com.ecommerce_platform.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a payment transaction for an order.
 * <p>
 *
 * Relationships:
 * - One-to-One with Order: Each payment is tied to a single order.
 * <p>
 *
 * Annotations:
 * @Entity marks this class as a persistent entity.
 * @Table(name = "payments") specifies the table name.
 * @OneToOne uses EAGER fetching because payment details are critical to be fetched when retrieving an order.
 * <p>
 *
 * Why EAGER fetching?
 * - Payments are tightly coupled with orders, so fetching payment details immediately is often necessary.
 * - Optimized for scenarios where payment and order information are frequently accessed together.
 */
@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private BigDecimal amount;

    private LocalDateTime paymentDate;

    private String paymentMethod;
}