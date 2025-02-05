package com.ecommerce_platform.repository.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents an entry in the user's shopping cart.
 * <p>
 *
 * Relationships:
 * - Many-to-One with User: A user can have multiple cart entries.
 * - Many-to-One with Product: A cart entry contains one product.
 * - Many-to-One with Order: Links cart items to an order once confirmed.
 * <p>
 *
 * Annotations:
 * @Entity marks this class as a persistent entity.
 * @Table(name = "cart") specifies the table name.
 * @ManyToOne uses LAZY fetching for performance optimization (cart items are fetched on-demand).
 * <p>
 *
 * Why LAZY fetching?
 * - Fetch cart-related objects only when necessary, preventing unnecessary data loading.
 * - Improves performance when loading cart objects without needing the entire object graph.
 */
@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;

    public Cart(User user, Product product, Integer quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }
}