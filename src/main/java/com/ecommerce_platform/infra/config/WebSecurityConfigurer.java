package com.ecommerce_platform.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class WebSecurityConfigurer {

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {

        final String[] authWhitelist = {
                "/swagger-ui/**", "/v3/api-docs/**", "/db-console/**", "/api/auth/**"
        };

        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.ignoringRequestMatchers("/db-console/**", "/swagger-ui/**", "/v3/api-docs/**"))
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())) // For H2 console
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(authWhitelist).permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}