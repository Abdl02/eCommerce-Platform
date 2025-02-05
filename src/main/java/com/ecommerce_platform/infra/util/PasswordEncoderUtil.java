package com.ecommerce_platform.infra.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    public static String encodePassword(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }
}