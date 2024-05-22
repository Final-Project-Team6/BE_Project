package com.fastcampus.aptner.jwt.service;

import com.fastcampus.aptner.jwt.domain.TokenStorage;
import com.fastcampus.aptner.jwt.dto.TokenStorageDto;
import com.fastcampus.aptner.jwt.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenStorage addRefreshToken(TokenStorage tokenStorage) {
        return refreshTokenRepository.save(tokenStorage);
    }

    @Transactional
    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepository.findByRefreshToken(refreshToken).ifPresent(refreshTokenRepository::delete);
    }

    @Transactional(readOnly = true)
    public Optional<TokenStorage> findRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    @Transactional
    public void saveNewRefreshToken(TokenStorageDto tokenStorageDto) {
        // 기존 리프레시 토큰 삭제
        refreshTokenRepository.findByMemberId(tokenStorageDto.getMemberId()).ifPresent(refreshTokenRepository::delete);

        // 새로운 리프레시 토큰 저장
        TokenStorage tokenStorage = TokenStorage.builder()
                .refreshToken(tokenStorageDto.getRefreshToken())
                .memberId(tokenStorageDto.getMemberId())
                .build();

        refreshTokenRepository.save(tokenStorage);
    }
}