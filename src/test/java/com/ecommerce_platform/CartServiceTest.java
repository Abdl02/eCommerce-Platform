package com.ecommerce_platform;

import com.ecommerce_platform.api.dto.request.CartUpdateRequest;
import com.ecommerce_platform.api.dto.response.CartResponse;
import com.ecommerce_platform.repository.entity.Cart;
import com.ecommerce_platform.repository.entity.Product;
import com.ecommerce_platform.repository.entity.User;
import com.ecommerce_platform.infra.exception.CartItemNotFoundException;
import com.ecommerce_platform.infra.exception.ProductNotFoundException;
import com.ecommerce_platform.infra.exception.UserNotFoundException;
import com.ecommerce_platform.infra.mapper.CartMapper;
import com.ecommerce_platform.repository.repos.CartRepository;
import com.ecommerce_platform.repository.repos.ProductRepository;
import com.ecommerce_platform.repository.repos.UserRepository;
import com.ecommerce_platform.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartMapper cartMapper;

    private User user;
    private Product product;
    private Cart cartItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User(1L, "john_doe", "password123", "john.doe@example.com", null);
        product = new Product(1L, "Laptop", "Gaming Laptop", BigDecimal.valueOf(1000), 10);
        cartItem = new Cart(1L, user, null, product, 2);
    }

    @Test
    void addProductToCart_Success() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        // Return empty to simulate no existing cart item and create a new cart
        when(cartRepository.findByUserAndProduct(user, product)).thenReturn(Optional.empty());

        Cart newCart = new Cart(user, product, 2); // Simulate new cart item creation
        when(cartRepository.save(any(Cart.class))).thenReturn(newCart);
        when(cartMapper.toDto(newCart)).thenReturn(new CartResponse(1L, product.getId(), product.getName(), 2, product.getPrice()));

        // Act
        CartResponse response = cartService.addProductToCart(user.getId(), product.getId(), 2);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.quantity());
        assertEquals(product.getName(), response.productName());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void addProductToCart_UserNotFound() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> cartService.addProductToCart(user.getId(), product.getId(), 2));
    }

    @Test
    void addProductToCart_ProductNotFound() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> cartService.addProductToCart(user.getId(), product.getId(), 2));
    }

    @Test
    void updateProductQuantity_Success() {
        // Arrange
        CartUpdateRequest request = new CartUpdateRequest(product.getId(), 5);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(cartRepository.findByUserAndProduct(user, product)).thenReturn(Optional.of(cartItem));
        when(cartRepository.save(any(Cart.class))).thenReturn(cartItem);
        when(cartMapper.toDto(cartItem)).thenReturn(new CartResponse(1L, 1L, "Laptop", 5, BigDecimal.valueOf(1000)));

        // Act
        CartResponse response = cartService.updateProductQuantity(user.getId(), request);

        // Assert
        assertNotNull(response);
        assertEquals(5, response.quantity());
        verify(cartRepository, times(1)).save(cartItem);
    }

    @Test
    void removeProductFromCart_Success() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(cartRepository.findByUserAndProduct(user, product)).thenReturn(Optional.of(cartItem));

        // Act
        cartService.removeProductFromCart(user.getId(), product.getId());

        // Assert
        verify(cartRepository, times(1)).delete(cartItem);
    }

    @Test
    void removeProductFromCart_CartItemNotFound() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(cartRepository.findByUserAndProduct(user, product)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CartItemNotFoundException.class, () -> cartService.removeProductFromCart(user.getId(), product.getId()));
    }
}
