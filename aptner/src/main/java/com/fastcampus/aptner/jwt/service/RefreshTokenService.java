package com.fastcampus.aptner.jwt.service;

import com.fastcampus.aptner.jwt.dto.TokenStorageDto;
import com.fastcampus.aptner.jwt.util.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final static String REFRESH_TOKEN_PREFIX = "refreshToken:";

    public void addRefreshToken(Long memberId, String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + memberId;
        redisTemplate.opsForValue().set(key, refreshToken, JwtTokenizer.REFRESH_TOKEN_EXPIRE_COUNT, TimeUnit.MILLISECONDS);
    }

    public Optional<String> findRefreshToken(Long memberId) {
        String key = REFRESH_TOKEN_PREFIX + memberId;
        String refreshToken = (String) redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(refreshToken);
    }

    public void deleteRefreshToken(Long memberId) {
        String key = REFRESH_TOKEN_PREFIX + memberId;
        redisTemplate.delete(key);
    }

    public void saveNewRefreshToken(TokenStorageDto tokenStorageDto) {
        Long memberId = tokenStorageDto.getMemberId();
        String newRefreshToken = tokenStorageDto.getRefreshToken();

        deleteRefreshToken(memberId);
        addRefreshToken(memberId, newRefreshToken);
    }
}