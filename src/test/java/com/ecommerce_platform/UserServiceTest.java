package com.ecommerce_platform;

import com.ecommerce_platform.api.dto.response.UserResponse;
import com.ecommerce_platform.infra.exception.UserNotFoundException;
import com.ecommerce_platform.repository.entity.User;
import com.ecommerce_platform.infra.mapper.UserMapper;
import com.ecommerce_platform.repository.repos.UserRepository;
import com.ecommerce_platform.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // initialize the mock
        user = new User(1L, "john_doe", "password123", "john.doe@example.com", null);
    }

    @Test
    void getUserById_Success() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(new UserResponse(1L, "john_doe", "john.doe@example.com"));

        UserResponse response = userService.getUserById(user.getId());

        assertNotNull(response);
        assertEquals("john_doe", response.username());
        assertEquals("john.doe@example.com", response.email());
    }

    @Test
    void getUserById_UserNotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(user.getId()));
    }

    @Test
    void updateUser_Success() {
        String newEmail = "new.email@example.com";
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(new UserResponse(1L, "john_doe", newEmail));

        UserResponse response = userService.updateUser(user.getId(), newEmail);

        assertNotNull(response);
        assertEquals(newEmail, response.email());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser_UserNotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(user.getId(), "new.email@example.com"));
    }

    @Test
    void deleteUser_Success() {
        when(userRepository.existsById(user.getId())).thenReturn(true);
        userService.deleteUser(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    void deleteUser_UserNotFound() {
        when(userRepository.existsById(user.getId())).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(user.getId()));
    }
}