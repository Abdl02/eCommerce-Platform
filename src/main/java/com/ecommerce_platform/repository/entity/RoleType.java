package com.ecommerce_platform.repository.entity;

/**
 * Enum representing user roles within the application.
 * <p>
 * Why an Enum?
 * - Provides compile-time safety and ensures only valid roles are assigned.
 * - Improves code readability and prevents errors from using arbitrary role names.
 */
public enum RoleType {
    ADMIN,
    CUSTOMER
}