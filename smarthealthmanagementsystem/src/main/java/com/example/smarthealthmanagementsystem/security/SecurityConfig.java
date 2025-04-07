package com.example.smarthealthmanagementsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for Spring Security.
 * Defines security configurations for the application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Creates a PasswordEncoder bean for encoding user passwords.
     *
     * @return BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates a UserDetailsService bean for managing user authentication.
     * In this example, an in-memory user is created for demonstration purposes.
     *
     * @param passwordEncoder Password encoder to encode the user's password.
     * @return InMemoryUserDetailsManager instance.
     */
    @Bean
    public UserDetailsService users(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    /**
     * Configures the security filter chain.
     * Defines authorization rules for different API endpoints.
     *
     * @param http HttpSecurity instance for configuring security.
     * @return SecurityFilterChain instance.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // For simplicity in API testing
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // You can be more specific if needed:
                        //.requestMatchers("/api/admin/users/**").hasRole("ADMIN")
                        // .requestMatchers("/api/admin/doctors/**").hasRole("ADMIN")
                        // .requestMatchers("/api/admin/nurses/**").hasRole("ADMIN")
                        // .requestMatchers("/api/admin/patients/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic.realmName("Smart Health System"));
        return http.build();
    }
}