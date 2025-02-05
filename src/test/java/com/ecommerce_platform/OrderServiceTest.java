package com.ecommerce_platform;

import com.ecommerce_platform.api.dto.request.OrderItemRequest;
import com.ecommerce_platform.api.dto.request.OrderRequest;
import com.ecommerce_platform.api.dto.response.OrderResponse;
import com.ecommerce_platform.infra.exception.OutOfStockException;
import com.ecommerce_platform.repository.entity.Order;
import com.ecommerce_platform.repository.entity.OrderStatus;
import com.ecommerce_platform.repository.entity.Product;
import com.ecommerce_platform.repository.entity.User;
import com.ecommerce_platform.infra.mapper.OrderMapper;
import com.ecommerce_platform.repository.repos.OrderRepository;
import com.ecommerce_platform.repository.repos.ProductRepository;
import com.ecommerce_platform.repository.repos.UserRepository;
import com.ecommerce_platform.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderMapper orderMapper;

    private User user;
    private Product product;
    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // initialize the mock
        user = new User(1L, "john_doe", "password123", "john.doe@example.com", null);
        product = new Product(1L, "Laptop", "Gaming Laptop", BigDecimal.valueOf(1000), 5);
        order = new Order(1L, user, LocalDateTime.now(), OrderStatus.PENDING, BigDecimal.valueOf(1000), null);
    }

    @Test
    void createOrder_Success() {
        OrderItemRequest itemRequest = new OrderItemRequest(product.getId(), 2);
        OrderRequest request = new OrderRequest(user.getId(), List.of(itemRequest));

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toDto(any(Order.class))).thenReturn(new OrderResponse(1L, LocalDateTime.now(), "PENDING", BigDecimal.valueOf(2000), Collections.emptyList()));

        OrderResponse response = orderService.createOrder(request);

        assertNotNull(response);
        assertEquals("PENDING", response.status());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void createOrder_OutOfStock() {
        OrderItemRequest itemRequest = new OrderItemRequest(product.getId(), 10);
        OrderRequest request = new OrderRequest(user.getId(), List.of(itemRequest));

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        assertThrows(OutOfStockException.class, () -> orderService.createOrder(request));
    }

    @Test
    void getAllOrders_Success() {
        when(orderRepository.findAll()).thenReturn(List.of(order));
        when(orderMapper.toDto(order)).thenReturn(new OrderResponse(1L, LocalDateTime.now(), "PENDING", BigDecimal.valueOf(1000), Collections.emptyList()));

        List<OrderResponse> responses = orderService.getAllOrders();

        assertEquals(1, responses.size());
        assertEquals("PENDING", responses.get(0).status());
    }
}