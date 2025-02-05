package com.ecommerce_platform.infra.mapper;

import com.ecommerce_platform.api.dto.response.CartResponse;
import com.ecommerce_platform.repository.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartResponse toDto(Cart cart);
}