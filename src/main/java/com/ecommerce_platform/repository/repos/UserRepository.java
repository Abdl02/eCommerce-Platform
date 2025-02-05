package com.ecommerce_platform.repository.repos;

import com.ecommerce_platform.repository.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Cacheable(key = "#username")
    Optional<User> findByUsernameIgnoreCase(String username);

    @Modifying
    @Transactional
    @CacheEvict(key = "#username")
    void deleteByUsername(String username);
}