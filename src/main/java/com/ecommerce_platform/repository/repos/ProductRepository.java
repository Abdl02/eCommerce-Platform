package com.ecommerce_platform.repository.repos;

import com.ecommerce_platform.repository.entity.Product;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Cacheable
    Optional<Product> findByName(String name);
}