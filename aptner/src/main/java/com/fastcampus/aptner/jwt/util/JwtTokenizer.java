package com.fastcampus.aptner.jwt.util;

import io.jsonwebtoken.Claims;
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

@Slf4j
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

    /**
     * AccessToken 생성
     */
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
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRE_COUNT))
                .signWith(getSigningKey(accessSecret))
                .compact();
    }

    /**
     * RefreshToken 생성
     */
    public String createRefreshToken(Long memberId,
                                     String username,
                                     String roleName,
                                     Long apartmentId) {
        Claims claims = Jwts.claims().setSubject(username);

        claims.put("roleName", roleName);
        claims.put("memberId", memberId);
        claims.put("username", username);
        claims.put("apartmentId", apartmentId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + REFRESH_TOKEN_EXPIRE_COUNT))
                .signWith(getSigningKey(refreshSecret))
                .compact();
    }

    /**
     * 토큰에서 유저 아이디 얻기
     */

    public Long getMemberIdFromToken(String token) {
        String[] tokenArr = token.split(" ");
        token = tokenArr[1];
        Claims claims = parseToken(token, accessSecret);
        return Long.valueOf((Integer)claims.get("memberId"));
    }

    public String getMemberRoleFromToken(String token) {
        String[] tokenArr = token.split(" ");
        token = tokenArr[1];
        Claims claims = parseToken(token, accessSecret);
        return String.valueOf(claims.get("roleName"));
    }


    public Claims parseAccessToken(String accessToken) {
        return parseToken(accessToken, accessSecret);
    }

    public Claims parseRefreshToken(String refreshToken) {
        return parseToken(refreshToken, refreshSecret);
    }


    public Claims parseToken(String token, byte[] secretKey) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (DecodingException e) {
            log.error("JWT 디코딩 오류: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("JWT 파싱 오류: {}", e.getMessage());
            throw new BadCredentialsException("Invalid token");
        }
    }

    /**
     * @param secretKey - byte형식
     * @return Key 형식 시크릿 키
     */
    public static Key getSigningKey(byte[] secretKey) {
        return Keys.hmacShaKeyFor(secretKey);
    }

}
