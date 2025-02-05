package com.ecommerce_platform.api.dto.response;

public record UserResponse(
        Long id,
        String username,
        String email
) {}