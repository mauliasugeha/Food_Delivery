package com.example.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 1. Rute untuk Customer (port 8080)
                .route("customer_route", r -> r.path("/customers", "/customers/**")
                        .uri("http://localhost:8080"))

                // 2. Rute untuk Order dan Produk (port 8080)
                .route("order_route", r -> r.path("/api/orders", "/api/orders/**")
                        .uri("http://localhost:8080"))
                .route("product_route", r -> r.path("/api/products", "/api/products/**")
                        .uri("http://localhost:8080"))

                // 3. Rute untuk Order API di ecommerce/order service (port 8081)
                .route("ecommerce_order_route", r -> r.path("/api/order", "/api/order/**")
                        .uri("http://localhost:8081"))

                // 4. Rute service terpisah
                .route("payment_route", r -> r.path("/payment/**")
                        .uri("http://localhost:8083"))
                .route("shipping_route", r -> r.path("/shipping/**")
                        .uri("http://localhost:8084"))

                .build();


    }

}
