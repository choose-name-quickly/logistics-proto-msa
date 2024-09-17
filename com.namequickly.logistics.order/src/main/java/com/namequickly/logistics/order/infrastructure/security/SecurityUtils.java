package com.namequickly.logistics.order.infrastructure.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static CustomUserDetails getCurrentUserDetails() {
        System.out.println(
            (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal());
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
    }
}
