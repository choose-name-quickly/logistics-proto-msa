package com.namequickly.logistics.auth.application.dto;

import com.namequickly.logistics.common.shared.UserRole;

// LocalDateTime 을 제거한 redis 캐싱용 Dto
public record UserDto(
    String username,
    String password,
    UserRole role
) {

}
