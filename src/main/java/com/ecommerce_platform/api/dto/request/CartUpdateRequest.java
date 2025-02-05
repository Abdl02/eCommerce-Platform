package com.ecommerce_platform.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record CartUpdateRequest(
        @NotNull Long productId,
        @NotNull Integer quantity
) {}