package com.ecommerce_platform.api.controller;

import com.ecommerce_platform.api.dto.request.LoginRequest;
import com.ecommerce_platform.api.dto.request.RegisterRequest;
import com.ecommerce_platform.api.dto.response.UserResponse;
import com.ecommerce_platform.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Registers a user and returns the user's details.")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid RegisterRequest request) {
        UserResponse response = authService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok("Login successful");
    }
}