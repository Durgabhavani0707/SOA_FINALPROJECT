package com.example.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {

        System.out.println(">>> CUSTOM GATEWAY ROUTES LOADED <<<");

        return builder.routes()

                .route("auth-service", r -> r
                        .path("/auth/**")
                        .uri("http://localhost:8081"))

                .route("vehicle-service", r -> r
                        .path("/vehicles/**")
                        .uri("lb://VEHICLE-SERVICE"))

                .route("trip-service", r -> r
                        .path("/trips/**")
                        .uri("lb://TRIP-SERVICE"))

                .route("maintenance-service", r -> r
                        .path("/maintenance/**")
                        .uri("lb://MAINTENANCE-SERVICE"))

                .build();
    }
}