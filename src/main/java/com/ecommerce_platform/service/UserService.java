package com.ecommerce_platform.service;

import com.ecommerce_platform.api.dto.response.UserResponse;
import com.ecommerce_platform.repository.entity.User;
import com.ecommerce_platform.infra.exception.UserNotFoundException;
import com.ecommerce_platform.infra.mapper.UserMapper;
import com.ecommerce_platform.repository.repos.UserRepository;
import com.ecommerce_platform.infra.util.LoggerUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
        LoggerUtil.logAction("Retrieved user with ID: " + userId + " and Username: " + user.getUsername());
        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponse updateUser(Long userId, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        user.setEmail(email);
        User updatedUser = userRepository.save(user);
        LoggerUtil.logAction("Updated email for user ID: " + userId + " to " + email);
        return userMapper.toResponse(updatedUser);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            LoggerUtil.logAction("Attempted to delete non-existent user with ID: " + userId);
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
        userRepository.deleteById(userId);
        LoggerUtil.logAction("Deleted user with ID: " + userId);
    }
}