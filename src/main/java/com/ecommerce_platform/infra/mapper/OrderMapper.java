package com.ecommerce_platform.infra.mapper;

import com.ecommerce_platform.api.dto.response.OrderResponse;
import com.ecommerce_platform.repository.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toDto(Order order);
}