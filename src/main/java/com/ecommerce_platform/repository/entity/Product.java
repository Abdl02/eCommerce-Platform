package com.ecommerce_platform.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Represents a product in the e-commerce catalog.
 * <p>
 *
 * Relationships:
 * - No direct relationships are needed since products are referenced by cart and orders.
 * <p>
 *
 * Annotations:
 * @Entity marks this class as a persistent entity.
 * @Table(name = "products") specifies the table name.
 * <p>
 *
 * Why no relationships?
 * - Products are referenced by other entities like Cart and Order, so they don’t directly manage these associations.
 * - Simplifies management of product data without overloading the entity with relationships.
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;
}