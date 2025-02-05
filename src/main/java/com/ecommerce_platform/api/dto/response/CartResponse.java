package com.ecommerce_platform.api.dto.response;

import java.math.BigDecimal;

public record CartResponse(
        Long cartId,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal price
) {}