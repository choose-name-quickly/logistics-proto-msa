package com.namequickly.logistics.gateway.filter;

import com.namequickly.logistics.gateway.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * JWT 검증하고 헤더에 유저정보 껴서 라우팅할 서비스의 헤더에 껴주는 필터
 * (굳이 인증인가가 필요없는 서비스가 있을 수 있으니 글로벌 필터가 아닌 게이트웨이 필터를 선택)
 */
@Slf4j(topic = "jwtAuthGatewayFilter")
@RequiredArgsConstructor
@Component
public class JwtAuthGatewayFilter implements GatewayFilter {

    private final JwtUtil jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders(); // 헤더
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION); // 헤더의 토큰
        Claims claims = jwtUtils.isTokenValid(token); // 토큰의 claims ( 실제론 getPayload() )

        log.info("{}",headers);
        log.info("{}",token);
        log.info("{}",claims); // TODO : <-- gateway 다 괜찮은데 요기 null 뜸 Auth 만들면서 gateway 가 잘못된건가 다시해봐야함

        // JWT 토큰 검증
        if (token != null && claims != null) {
            String username = jwtUtils.getUsernameFromToken(token); // 유저이름 추출
            String roles = jwtUtils.getRoleFromToken(token); // 유저권한 추출

            exchange.getRequest().mutate()
                .header("X-User-Name", username)
                .header("X-User-Roles", roles)
                .build();

            return chain.filter(exchange);
        }

        return Mono.error(new RuntimeException("Invalid Token"));
    }

    // 리팩토링 예정 redisUtil을 통해 해당 사용자가 로그아웃했는지 확인 후, 로그아웃한 사용자라면 예외를 발생
    // - access, refresh 토큰을 활용한다
    // - VALID, EXPIRED, INVALID 상태를 나눠 대응한다

}
