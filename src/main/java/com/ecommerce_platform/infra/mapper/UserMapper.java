package com.ecommerce_platform.infra.mapper;

import com.ecommerce_platform.api.dto.response.UserResponse;
import com.ecommerce_platform.repository.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
}