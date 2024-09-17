package com.namequickly.logistics.order.infrastructure.filter;

import com.namequickly.logistics.order.infrastructure.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        String roleHeader = request.getHeader("X-User-Roles");
        String affiliationId = request.getHeader("X-User-AffiliationId");

        if (username != null && roleHeader != null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleHeader.trim());

            CustomUserDetails customUserDetails = new CustomUserDetails(username, affiliationId,
                authority);

            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(customUserDetails, null,
                    customUserDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                null,
                null,
                AuthorityUtils.NO_AUTHORITIES
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // 필터 체인 진행
        filterChain.doFilter(request, response);
    }
}