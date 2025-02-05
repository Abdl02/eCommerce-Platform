package com.ecommerce_platform.repository.repos;

import com.ecommerce_platform.repository.entity.Order;
import com.ecommerce_platform.repository.entity.OrderStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Order} entities in the database.
 *
 * <p>This interface extends {@link JpaRepository} to provide basic CRUD operations.
 * It also contains custom methods to interact with the orders, such as retrieving
 * orders by status and updating the order status directly through a query.</p>
 *
 * <p>The following key methods are available:</p>
 * <ul>
 *     <li>{@link #findAllByStatus(OrderStatus)}: Retrieves a list of orders based on their status.</li>
 *     <li>{@link #updateOrderStatus(Long, OrderStatus)}: Updates the status of a specific order.</li>
 * </ul>
 *
 * <p>Annotations:</p>
 * <ul>
 *     <li>{@link Repository}: Indicates that this interface is a Spring Data repository.</li>
 *     <li>{@link Cacheable}: Enables caching for the {@code findAllByStatus} method to improve performance.</li>
 *     <li>{@link Modifying}: Marks the custom query for modifying data in the database.</li>
 * </ul>
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Cacheable
    List<Order> findAllByStatus(OrderStatus status);

    @Modifying
    @Query("UPDATE Order o SET o.status = ?2 WHERE o.id = ?1")
    void updateOrderStatus(Long orderId, OrderStatus status);
}