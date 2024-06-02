package com.fastcampus.aptner.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenizer {

    private final byte[] accessSecret;
    private final byte[] refreshSecret;

    public final static Long ACCESS_TOKEN_EXPIRE_COUNT = 30 * 60 * 1000L; // 30 minutes
    public final static Long REFRESH_TOKEN_EXPIRE_COUNT = 7 * 24 * 60 * 60 * 1000L; // 7 days

    public JwtTokenizer(@Value("${jwt.secretKey}") String accessSecret, @Value("${jwt.refreshKey}") String refreshSecret) {
        this.accessSecret = accessSecret.getBytes(StandardCharsets.UTF_8);
        this.refreshSecret = refreshSecret.getBytes(StandardCharsets.UTF_8);
    }

    public String createAccessToken(Long memberId,
                                    String username,
                                    String roleName,
                                    String apartmentName,
                                    Long apartmentId) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roleName", roleName);
        claims.put("memberId", memberId);
        claims.put("username", username);
        claims.put("apartmentName", apartmentName);
        claims.put("apartmentId", apartmentId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_COUNT))
                .signWith(getSigningKey(accessSecret))
                .compact();
    }

    public String createRefreshToken(Long memberId,
                                     String username,
                                     String roleName,
                                     String apartmentName,
                                     Long apartmentId) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roleName", roleName);
        claims.put("memberId", memberId);
        claims.put("username", username);
        claims.put("apartmentName", apartmentName);
        claims.put("apartmentId", apartmentId);

        String uniqueId = UUID.randomUUID().toString();

        return Jwts.builder()
                .setId(uniqueId)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_COUNT))
                .signWith(getSigningKey(refreshSecret))
                .compact();
    }

    public Claims parseRefreshToken(String refreshToken) {
        return parseToken(refreshToken, refreshSecret);
    }

    private Claims parseToken(String token, byte[] secretKey) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "JWT 토큰이 만료되었습니다.");
        } catch (Exception e) {
            throw new BadCredentialsException("유효하지 않은 토큰입니다.");
        }
    }

    private Key getSigningKey(byte[] secretKey) {
        return Keys.hmacShaKeyFor(secretKey);
    }

    public Long getMemberIdFromToken(String token) {
        Claims claims = parseRefreshToken(token);
        return claims.get("memberId", Long.class);
    }

    public Claims parseAccessToken(String accessToken) {
        return parseToken(accessToken, accessSecret);
    }
}