package com.fastcampus.aptner.jwt.controller;

import com.fastcampus.aptner.apartment.service.FindApartmentServiceImpl;
import com.fastcampus.aptner.global.handler.exception.CustomAPIException;
import com.fastcampus.aptner.jwt.dto.RefreshTokenRequest;
import com.fastcampus.aptner.jwt.dto.TokenStorageDto;
import com.fastcampus.aptner.jwt.service.RefreshTokenService;
import com.fastcampus.aptner.jwt.util.JwtTokenizer;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.dto.HttpResponse;
import com.fastcampus.aptner.member.dto.response.LoginMemberResponse;
import com.fastcampus.aptner.member.service.FindMemberRoleServiceImpl;
import com.fastcampus.aptner.member.service.LoginMemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "JWT 리프레시 토큰 재발급(사용자, 관리자)", description = "JWT AccessToken, RefreshToken 토큰 재발급")
@RequiredArgsConstructor
@RequestMapping("/api/token")
@RestController
public class RefreshTokenController {

    private final JwtTokenizer jwtTokenizer;
    private final LoginMemberService memberService;
    private final RefreshTokenService refreshTokenService;
    private final FindMemberRoleServiceImpl memberRoleService;
    private final FindApartmentServiceImpl apartmentService;

    @Operation(
            summary = "액세스, 리프레시 토큰 재생성 API",
            description = "Schema -> JWT AccessToken, RefreshToken 재발급 \n\n " +
                    "refreshToken : 이전에 사용한 리프레시 토큰 입력(필수)"
    )
    @PostMapping("/refresh/reissue")
    public ResponseEntity<?> requestRefresh(@RequestBody RefreshTokenRequest request) {
        String oldRefreshToken = request.getRefreshToken();

        Claims claims;
        try {
            claims = jwtTokenizer.parseRefreshToken(oldRefreshToken);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(new HttpResponse<>(-1, "리프레시 토큰이 만료되었습니다.", null), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new HttpResponse<>(-1, "유효하지 않은 리프레시 토큰입니다.", null), HttpStatus.BAD_REQUEST);
        }

        Long memberId = claims.get("memberId", Long.class);

        // 저장된 리프레시 토큰을 확인합니다.
        String storedRefreshToken = refreshTokenService.findRefreshToken(memberId)
                .orElseThrow(() -> new IllegalArgumentException("리프레시 토큰을 찾을 수 없습니다."));

        if (!storedRefreshToken.equals(oldRefreshToken)) {
            return new ResponseEntity<>(new HttpResponse<>(-1, "유효하지 않은 리프레시 토큰입니다.", null), HttpStatus.BAD_REQUEST);
        }

        Member member = memberService.findMemberById(memberId)
                .orElseThrow(() -> new CustomAPIException("회원이 존재하지 않습니다."));

        String username = claims.getSubject();
        Long apartmentId = claims.get("apartmentId", Long.class);
        String apartmentName = apartmentService.findApartmentById(apartmentId).getName();
        String memberRole = memberRoleService.getMemberRole(memberId, apartmentId).toString();

        String newAccessToken = jwtTokenizer.createAccessToken(memberId, username, memberRole, apartmentName, apartmentId);
        String newRefreshToken = jwtTokenizer.createRefreshToken(memberId, username, memberRole, apartmentName, apartmentId);

        TokenStorageDto tokenStorageDto = TokenStorageDto.builder()
                .memberId(memberId)
                .refreshToken(newRefreshToken)
                .build();

        refreshTokenService.saveNewRefreshToken(tokenStorageDto);

        LoginMemberResponse loginResponse = LoginMemberResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .memberId(memberId)
                .nickname(member.getNickname())
                .build();

        return new ResponseEntity<>(new HttpResponse<>(1, "리프레시 토큰이 재발급 되었습니다.", loginResponse), HttpStatus.OK);
    }
}