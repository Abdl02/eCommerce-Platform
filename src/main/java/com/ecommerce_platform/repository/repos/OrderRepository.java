package com.ecommerce_platform.repository.repos;

import com.ecommerce_platform.repository.entity.Order;
import com.ecommerce_platform.repository.entity.OrderStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Cacheable
    List<Order> findAllByStatus(OrderStatus status);

    @Modifying
    @Query("UPDATE Order o SET o.status = ?2 WHERE o.id = ?1")
    void updateOrderStatus(Long orderId, OrderStatus status);
}