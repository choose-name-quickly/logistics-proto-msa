package com.namequickly.logistics.auth.infrastructure.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.namequickly.logistics.auth.application.dto.UserLoginRequestDto;
import com.namequickly.logistics.common.response.CommonResponse;
import com.namequickly.logistics.common.shared.UserRole;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "JwtAuthenticationFilter - 로그인 및 JWT 생성")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    @PostConstruct
    void setup() {
        setFilterProcessesUrl("/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            UserLoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
                UserLoginRequestDto.class);
            log.info(requestDto.toString());

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    requestDto.username(),
                    requestDto.password(),
                    null
                )
            );
            // TODO : 필터 레벨에서 동일한 양식의 에러 응답을 하려면 에러 처리 필터를 만든 후, 필터 순서를 가장 앞에 둔 다음, 직접 ObjectMapper로 json으로 직렬화 하시면 됩니다..!
            // 예전에 제가 구현한 코드 참고하시면 좋을 것 같아요 (https://github.com/yun-studio/insight-backend/blob/main/src/main/java/com/yunstudio/insight/global/exception/ExceptionHandlerFilter.java)
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRole role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(username, role);
        jwtUtil.addJwtToCookie(token, response);

        log.info("JWT 토큰 {}",token);
        // 로그인 결과 화면 보여주기
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        CommonResponse.success("로그인 성공"); // TODO : dto 만들어 토큰까지 담을수도 있음
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed)
        throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}