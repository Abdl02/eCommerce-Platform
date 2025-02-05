package com.ecommerce_platform.api.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OrderRequest(
        @NotNull Long userId,
        @NotNull List<OrderItemRequest> items
) {}