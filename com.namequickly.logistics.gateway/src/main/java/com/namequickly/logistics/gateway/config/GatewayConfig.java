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

        // 요게 본체
        return builder.routes()
            .route("hub-service", r -> r.path("/api/hubs/**")
                .filters(f -> f.filter(authFilter))
                .uri("lb://hub-service")
            )
            .route("order-service", r -> r.path("/api/orders/**")
                .filters(f -> f.filter(authFilter))
                .uri("lb://order-service")
            )
            .build();
    }
}