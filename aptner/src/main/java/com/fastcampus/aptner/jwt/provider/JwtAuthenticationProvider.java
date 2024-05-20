package com.fastcampus.aptner.jwt.provider;

import com.fastcampus.aptner.jwt.token.JwtAuthenticationToken;
import com.fastcampus.aptner.jwt.util.JwtTokenizer;
import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        Long apartmentId = claims.get("apartmentId", Long.class);
        String roleName = claims.get("roleName", String.class);

        // 권한 설정
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleName);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);

        JWTMemberInfoDTO JWTMemberInfoDTO = new JWTMemberInfoDTO();
        JWTMemberInfoDTO.setMemberId(memberId);
        JWTMemberInfoDTO.setUsername(username);
        JWTMemberInfoDTO.setApartmentName(apartmentName);
        JWTMemberInfoDTO.setApartmentId(apartmentId);
        JWTMemberInfoDTO.setRoleName(roleName);

        System.out.println("memberInfoRequest.getMemberId() = " + JWTMemberInfoDTO.getMemberId());
        System.out.println("memberInfoRequest.getApartmentName() = " + JWTMemberInfoDTO.getApartmentName());
        System.out.println("memberInfoRequest.getRoleName() = " + JWTMemberInfoDTO.getRoleName());

        return new JwtAuthenticationToken(authorities, JWTMemberInfoDTO, null);
    }

    private List<GrantedAuthority> getGrantedAuthorities(Claims claims) {
        String roleName = (String) claims.get("roleName");
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleName);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        System.out.println("authorities.get(0) = " + authorities.get(0));
        return authorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
