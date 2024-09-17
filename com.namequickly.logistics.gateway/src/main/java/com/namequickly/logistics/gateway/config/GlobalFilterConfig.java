package com.namequickly.logistics.gateway.config;

import static com.namequickly.logistics.gateway.utils.JwtUtil.AUTHORIZATION_KEY;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

/**
 * 글로벌 필터로 로깅을 찍어준다
 */
@Configuration
public class GlobalFilterConfig {

    public static final String AUTHORIZATION_KEY = "auth"; // JWT 내의 사용자 권한 값의 KEY
    public static final String BEARER_PREFIX = "Bearer "; // Token 식별자

    @Value("${jwt.secret.key}")
    private String JWT_SECRET_KEY; // JWT 생성 및 검증에 사용할 비밀키 (Base 64로 Encode)
    private SecretKey secretKey; // JWT 생성 및 검증에 사용할 Key 인터페이스를 구현한 객체
    private JwtParser jwtParser; // JWT 검증을 위한 JwtParser 객체 (유효성 검사 및 Payload 추출)

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(JWT_SECRET_KEY); // secretKey -> Base64 디코딩하여 byte 배열로 저장
        secretKey = Keys.hmacShaKeyFor(bytes);
        jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }

    // 사실 필요 없는 jwt 생성 연습용 코드
    private String createToken(String nickname, String role, long expiration) {
        Date now = new Date();

        return Jwts.builder()
            .subject(nickname)
            .claim(AUTHORIZATION_KEY, role)
            .expiration(new Date(now.getTime() + expiration))
            .issuedAt(now)
            .signWith(secretKey, SIG.HS256)
            .compact();
    }

    @Bean
    public WebFilter customGlobalFilter() {

        return (exchange, chain) -> {
            System.out.println("Global filter: Request received at " + exchange.getRequest().getURI());
            System.out.println(createToken("testUser","MASTER",10000));
            return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> System.out.println("Global filter: Response sent with status " + exchange.getResponse().getStatusCode())));
        };
    }

}

