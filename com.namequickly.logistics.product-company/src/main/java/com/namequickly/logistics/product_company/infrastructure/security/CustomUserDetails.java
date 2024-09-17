package com.namequickly.logistics.product_company.infrastructure.security;


import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String affiliationId;  // 소속 ID는 별도로 분리
    private final GrantedAuthority authority;  // 하나의 권한만 관리

    public CustomUserDetails(String username, String affiliationId, GrantedAuthority authority) {
        this.username = username;
        this.affiliationId = affiliationId;
        this.authority = authority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(authority);  // 하나의 권한을 리스트로 반환
    }

    public String getRoleAsString() {
        return authority.getAuthority();
    }

    @Override
    public String getPassword() {
        return "";  // 비밀번호가 없으면 빈 값
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}