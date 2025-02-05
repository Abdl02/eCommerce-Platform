package com.ecommerce_platform.infra.mapper;

import com.ecommerce_platform.api.dto.request.ProductRequest;
import com.ecommerce_platform.api.dto.response.ProductResponse;
import com.ecommerce_platform.repository.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequest request);

    ProductResponse toDto(Product product);
}