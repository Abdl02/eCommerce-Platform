package com.ecommerce_platform.infra.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {

    public static String encodePassword(String plainPassword) {
        return new BCryptPasswordEncoder().encode(plainPassword);
    }
}