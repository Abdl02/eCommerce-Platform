package com.ecommerce_platform.repository.repos;

import com.ecommerce_platform.repository.entity.Role;
import com.ecommerce_platform.repository.entity.RoleType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Cacheable
    Optional<Role> findByName(RoleType name);
}