package com.example.order_management.security; // Sesuaikan package untuk ecommerce (com.example.foodDelivery.config)

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Endpoint yang boleh diakses tanpa login (misal lihat produk)
                        .requestMatchers("/api/products/**", "/api/orders","/api/order", "/orders", "/api/orders/**", "/payment/**", "/shipping/**").permitAll()
                        // Semua endpoint lain WAJIB pakai token dari Keycloak
                        .anyRequest().authenticated()
                )
                // Konfigurasi otomatis untuk membaca token JWT dari Keycloak
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
}