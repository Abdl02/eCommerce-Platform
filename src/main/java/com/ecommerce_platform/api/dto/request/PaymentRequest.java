package com.ecommerce_platform.api.dto.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PaymentRequest(
        @NotNull Long orderId,
        @NotNull BigDecimal amount,
        @NotNull String paymentMethod
) {}