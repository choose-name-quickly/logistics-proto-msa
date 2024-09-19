package com.namequickly.logistics.gateway.config;

import com.namequickly.logistics.gateway.filter.JwtAuthGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 라우팅시 authFilter 를 거치게 해서 JWT 를 검증하고 유저정보를 라우팅할 서비스에 넘긴다
 */
@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder, JwtAuthGatewayFilter authFilter) {

        return builder.routes()
            // hub
            .route("ai-service", r -> r.path("/api/ai/**")
                .filters(f -> f.filter(authFilter))
                .uri("lb://ai-service")
            )
            // hub
            .route("hub-service", r -> r.path("/api/hubs/**")
                .filters(f -> f.filter(authFilter))
                .uri("lb://hub-service")
            )
            // order
            .route("order-service", r -> r.path("/api/orders/**")
                .filters(f -> f.filter(authFilter))
                .uri("lb://order-service")
            )
            // hub-management
            .route("hub-management-service", r -> r.path("/api/hub-managemnet/**")
                .filters(f -> f.filter(authFilter))
                .uri("lb://hub-management-service")
            )
            // product-company
            .route("product-company-service", r -> r.path("/api/products/**")
                .filters(f -> f.filter(authFilter))
                .uri("lb://product-company-service")
            )
            // slack-message
            .route("slack-message-service", r -> r.path("/api/slack-message/**")
                .filters(f -> f.filter(authFilter))
                .uri("lb://slack-message-service")
            )
            .build();
    }
}