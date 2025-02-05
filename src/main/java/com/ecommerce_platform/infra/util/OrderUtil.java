package com.ecommerce_platform.infra.util;

import com.ecommerce_platform.repository.entity.Order;
import com.ecommerce_platform.repository.entity.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderUtil {

    public void validateOrderHasUser(Order order) {
        if (order.getUser() == null) {
            throw new IllegalStateException("Order must have a user associated.");
        }
    }

    public void updateOrderStatus(Order order, OrderStatus status) {
        validateOrderHasUser(order);
        order.setStatus(status);
    }
}