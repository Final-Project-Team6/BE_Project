package com.fastcampus.aptner.jwt.provider;

import com.fastcampus.aptner.jwt.token.JwtAuthenticationToken;
import com.fastcampus.aptner.jwt.util.JwtTokenizer;
import com.fastcampus.aptner.jwt.util.MemberLoginResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenizer jwtTokenizer;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;

        // JWT 토큰을 검증: 토큰 기간 만료 여부, 토큰 문자열 체크 등 Exception 이 발생되는 시점.
        Claims claims = jwtTokenizer.parseAccessToken(authenticationToken.getToken());

        // claims.getSubject() 으로 암호화된 데이터를 포함하고, 복호화 코드도 포함하기.
        String username = claims.getSubject();
        Long memberId = claims.get("memberId", Long.class);
        String apartmentName = claims.get("apartmentName", String.class);
        List<GrantedAuthority> authorities = getGrantedAuthorities(claims);

        MemberLoginResponse memberLoginResponse = new MemberLoginResponse();
        memberLoginResponse.setMemberId(memberId);
        memberLoginResponse.setUsername(username);
        memberLoginResponse.setApartmentName(apartmentName);

        return new JwtAuthenticationToken(authorities, memberLoginResponse, null);
    }

    private List<GrantedAuthority> getGrantedAuthorities(Claims claims) {
        List<String> roles = (List<String>) claims.get("roles");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(()-> role);
        }
        return authorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
