package com.ecommerce_platform.repository.entity;

/**
 * Enum representing the status of an order.
 * <p>
 *
 * Why an Enum?
 * - Provides type safety, ensuring only valid order statuses are used.
 * - Improves readability and maintainability compared to using plain strings or integers.
 */
public enum OrderStatus {
    PENDING,
    CONFIRMED,
    SHIPPED,
    DELIVERED,
    CANCELED
}