package com.ecommerce_platform.repository.repos;

import com.ecommerce_platform.repository.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities.
 * Provides methods to perform CRUD operations and custom queries related to users.
 * Includes caching support and modifying query capabilities.
 * <p>
 *
 * Key Features:
 * - Retrieve a user by username with caching.
 * - Delete a user by username with cache eviction.
 * <p>
 *
 * Annotations:
 * - {@code @Transactional}: Ensures the deletion is part of a database transaction,
 *   providing atomicity and rollback capabilities if needed.
 * - {@code @CacheEvict}: Automatically removes the cache entry associated with the
 *   given username after the delete operation is performed, ensuring cache consistency.
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Cacheable(key = "#username")
    Optional<User> findByUsernameIgnoreCase(String username);

    @Modifying
    @Transactional
    @CacheEvict(key = "#username")
    void deleteByUsername(String username);
}