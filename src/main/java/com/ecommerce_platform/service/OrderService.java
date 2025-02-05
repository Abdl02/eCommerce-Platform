package com.ecommerce_platform.service;

import com.ecommerce_platform.api.dto.request.OrderItemRequest;
import com.ecommerce_platform.api.dto.request.OrderRequest;
import com.ecommerce_platform.api.dto.response.OrderResponse;
import com.ecommerce_platform.repository.entity.Cart;
import com.ecommerce_platform.repository.entity.Order;
import com.ecommerce_platform.repository.entity.OrderStatus;
import com.ecommerce_platform.repository.entity.Product;
import com.ecommerce_platform.repository.entity.User;
import com.ecommerce_platform.infra.exception.OutOfStockException;
import com.ecommerce_platform.infra.exception.ProductNotFoundException;
import com.ecommerce_platform.infra.exception.UserNotFoundException;
import com.ecommerce_platform.infra.mapper.OrderMapper;
import com.ecommerce_platform.repository.repos.OrderRepository;
import com.ecommerce_platform.repository.repos.ProductRepository;
import com.ecommerce_platform.repository.repos.UserRepository;
import com.ecommerce_platform.infra.util.LoggerUtil;
import com.ecommerce_platform.infra.util.OrderUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException("User with ID " + request.userId() + " not found"));

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<Cart> orderItems = new ArrayList<>();

        for (OrderItemRequest item : request.items()) {
            Product product = fetchAndValidateProduct(item.productId(), item.quantity());
            product.setStock(product.getStock() - item.quantity());
            productRepository.save(product);

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(item.quantity()));
            totalAmount = totalAmount.add(itemTotal);

            orderItems.add(new Cart(user, product, item.quantity()));
        }

        Order order = new Order(null, user, LocalDateTime.now(), OrderStatus.PENDING, totalAmount, orderItems);
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDto(savedOrder);
    }

    private Product fetchAndValidateProduct(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));

        if (product.getStock() < quantity) {
            throw new OutOfStockException("Insufficient stock for product: " + product.getName());
        }
        return product;
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .toList();
    }
}