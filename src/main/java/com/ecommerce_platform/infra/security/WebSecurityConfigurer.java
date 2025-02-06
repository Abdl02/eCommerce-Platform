package com.ecommerce_platform.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 *
 * TODO: using JWT
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class WebSecurityConfigurer {

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {

        final String[] authWhitelist = {
                // Swagger and H2 Console
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/db-console/**",

                // Auth endpoints
                "/api/auth/**",

                // User, Product, Cart, Order, Payment endpoints
                "/api/users/**",
                "/api/products/**",
                "/api/carts/**",
                "/api/orders/**",
                "/api/payments/**"
        };

        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF globally if using POST requests without CSRF token
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())) // For H2 console
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(authWhitelist).permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}