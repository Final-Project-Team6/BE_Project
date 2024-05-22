package com.fastcampus.aptner.jwt.repository;

import com.fastcampus.aptner.jwt.domain.TokenStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<TokenStorage, Long> {
    Optional<TokenStorage> findByRefreshToken(String value);
    Optional<TokenStorage> findByMemberId(Long memberId);
}
