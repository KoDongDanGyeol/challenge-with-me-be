package com.example.challengewithmebe.global.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 1000L * 60 * 60 * 24 * 7)
public class RefreshToken {
    @Id
    private String token;
    private String memberId;

    public RefreshToken(String token, String memberId) {
        this.token = token;
        this.memberId = memberId;
    }
}