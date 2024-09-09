package com.namequickly.logistics.global.redis;

import com.namequickly.logistics.global.jwt.JwtUtil;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@RedisHash(value = "refreshToken", timeToLive = JwtUtil.REFRESH_TOKEN_TTL_SECONDS)
public class RefreshToken {

    @Id
    private String nickname;

    private String refreshToken;
}
