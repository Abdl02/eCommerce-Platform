package com.ecommerce_platform.repository.repos;

import com.ecommerce_platform.repository.entity.Cart;
import com.ecommerce_platform.repository.entity.Product;
import com.ecommerce_platform.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserAndProduct(User user, Product product);
    List<Cart> findByUser(User user);
}