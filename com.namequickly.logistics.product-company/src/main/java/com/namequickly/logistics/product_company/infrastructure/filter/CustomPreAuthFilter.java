package com.namequickly.logistics.product_company.infrastructure.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CustomPreAuthFilter extends OncePerRequestFilter {
    // TODO 나중에 성진님께서 어떤 값으로 넘기는지 확인 필요


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String username = request.getHeader("X-User-Name");
        String rolesHeader = request.getHeader("X-User-Roles");

        if (username != null && rolesHeader != null) {
            List<SimpleGrantedAuthority> authorities = Arrays.stream(rolesHeader.split(","))
                .map(role -> new SimpleGrantedAuthority(role.trim())).collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(null,
                null, AuthorityUtils.NO_AUTHORITIES);

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
