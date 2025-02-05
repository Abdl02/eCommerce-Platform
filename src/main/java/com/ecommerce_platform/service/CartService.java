package com.ecommerce_platform.service;

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
import com.ecommerce_platform.infra.util.LoggerUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Transactional
    public CartResponse addProductToCart(Long userId, Long productId, Integer quantity) {
        User user = getUserById(userId);
        Product product = getProductById(productId);

        Cart cartItem = findExistingCartItem(user, product)
                .orElse(createNewCartItem(user, product, quantity));

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        Cart savedCart = cartRepository.save(cartItem);

        LoggerUtil.logAction("Added product ID " + productId + " to cart for user ID " + userId);
        return cartMapper.toDto(savedCart);
    }

    @Transactional
    public CartResponse updateProductQuantity(Long userId, CartUpdateRequest request) {
        User user = getUserById(userId);
        Product product = getProductById(request.productId());

        Cart cartItem = findExistingCartItem(user, product)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));

        cartItem.setQuantity(request.quantity());
        Cart updatedCart = cartRepository.save(cartItem);

        return cartMapper.toDto(updatedCart);
    }

    @Transactional
    public void removeProductFromCart(Long userId, Long productId) {
        User user = getUserById(userId);
        Product product = getProductById(productId);

        Cart cartItem = findExistingCartItem(user, product)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));

        cartRepository.delete(cartItem);
    }

    public List<CartResponse> getCartItemsForUser(Long userId) {
        User user = getUserById(userId);
        return cartRepository.findByUser(user).stream()
                .map(cartMapper::toDto)
                .collect(Collectors.toList());
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));
    }

    private Optional<Cart> findExistingCartItem(User user, Product product) {
        return cartRepository.findByUserAndProduct(user, product);
    }

    private Cart createNewCartItem(User user, Product product, Integer quantity) {
        return new Cart(user, product, quantity);
    }
}