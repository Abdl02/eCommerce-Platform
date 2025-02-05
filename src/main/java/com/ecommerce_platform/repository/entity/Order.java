package com.ecommerce_platform.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a customer's order.
 * <p>
 *
 * Relationships:
 * - Many-to-One with User: A user can have multiple orders.
 * - One-to-Many with Cart: An order can include multiple cart items.
 * <p>
 *
 * Annotations:
 * @Entity marks this as a persistent entity.
 * @Table(name = "orders") specifies the table name.
 * @OneToMany uses LAZY fetching to prevent immediate loading of all cart items.
 * <p>
 *
 * Why LAZY fetching?
 * - Loading all cart items when accessing an order could be expensive.
 * - LAZY loading ensures that related data is fetched only when needed.
 * <p>
 *
 * Why @Enumerated(EnumType.STRING) for OrderStatus?
 * - Enum values are stored as strings, improving readability in the database (e.g., "PENDING" instead of an integer).
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Cart> items;
}