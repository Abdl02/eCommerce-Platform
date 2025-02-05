package com.ecommerce_platform;

import com.ecommerce_platform.api.dto.request.RegisterRequest;
import com.ecommerce_platform.api.dto.response.UserResponse;
import com.ecommerce_platform.repository.entity.Role;
import com.ecommerce_platform.repository.entity.RoleType;
import com.ecommerce_platform.repository.entity.User;
import com.ecommerce_platform.infra.exception.RoleNotFoundException;
import com.ecommerce_platform.infra.exception.UserAlreadyExistsException;
import com.ecommerce_platform.repository.repos.RoleRepository;
import com.ecommerce_platform.repository.repos.UserRepository;
import com.ecommerce_platform.service.AuthService;
import com.ecommerce_platform.infra.util.PasswordEncoderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_Success() {
        // Arrange
        RegisterRequest request = new RegisterRequest("john_doe", "password123", "john.doe@example.com");
        Role customerRole = new Role(1L, RoleType.CUSTOMER);

        when(userRepository.findByUsernameIgnoreCase(request.username())).thenReturn(Optional.empty());
        when(roleRepository.findByName(RoleType.CUSTOMER)).thenReturn(Optional.of(customerRole));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UserResponse response = authService.registerUser(request);

        // Assert
        assertNotNull(response);
        assertEquals("john_doe", response.username());
        assertEquals("john.doe@example.com", response.email());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_UserAlreadyExists() {
        // Arrange
        RegisterRequest request = new RegisterRequest("john_doe", "password123", "john.doe@example.com");

        when(userRepository.findByUsernameIgnoreCase(request.username())).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> authService.registerUser(request));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_RoleNotFound() {
        // Arrange
        RegisterRequest request = new RegisterRequest("john_doe", "password123", "john.doe@example.com");

        when(userRepository.findByUsernameIgnoreCase(request.username())).thenReturn(Optional.empty());
        when(roleRepository.findByName(RoleType.CUSTOMER)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RoleNotFoundException.class, () -> authService.registerUser(request));
        verify(userRepository, never()).save(any(User.class));
    }
}