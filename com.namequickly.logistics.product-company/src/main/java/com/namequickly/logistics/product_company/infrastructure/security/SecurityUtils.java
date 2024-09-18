package com.namequickly.logistics.product_company.infrastructure.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static CustomUserDetails getCurrentUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
    }
}
