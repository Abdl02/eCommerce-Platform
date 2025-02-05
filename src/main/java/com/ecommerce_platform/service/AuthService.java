package com.ecommerce_platform.service;

import com.ecommerce_platform.api.dto.request.RegisterRequest;
import com.ecommerce_platform.api.dto.response.UserResponse;
import com.ecommerce_platform.repository.entity.Role;
import com.ecommerce_platform.repository.entity.RoleType;
import com.ecommerce_platform.repository.entity.User;
import com.ecommerce_platform.infra.exception.RoleNotFoundException;
import com.ecommerce_platform.infra.exception.UserAlreadyExistsException;
import com.ecommerce_platform.repository.repos.RoleRepository;
import com.ecommerce_platform.repository.repos.UserRepository;
import com.ecommerce_platform.infra.util.LoggerUtil;
import com.ecommerce_platform.infra.util.PasswordEncoderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserResponse registerUser(RegisterRequest request) {
        if (userRepository.findByUsernameIgnoreCase(request.username()).isPresent()) {
            LoggerUtil.logAction("Failed registration attempt: Username " + request.username() + " already exists");
            throw new UserAlreadyExistsException("Username " + request.username() + " already exists");
        }

        Role userRole = roleRepository.findByName(RoleType.CUSTOMER)
                .orElseThrow(() -> new RoleNotFoundException("Customer role not found"));

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(PasswordEncoderUtil.encodePassword(request.password()));
        user.setEmail(request.email());
        user.setRoles(Collections.singleton(userRole));

        User savedUser = userRepository.save(user);
        LoggerUtil.logAction("Successfully registered user: " + savedUser.getUsername());
        return new UserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }
}