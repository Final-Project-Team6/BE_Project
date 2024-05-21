package com.fastcampus.aptner.jwt.controller;

import com.fastcampus.aptner.apartment.service.FindApartmentService;
import com.fastcampus.aptner.global.handler.exception.CustomAPIException;
import com.fastcampus.aptner.jwt.domain.TokenStorage;
import com.fastcampus.aptner.jwt.dto.RefreshTokenRequest;
import com.fastcampus.aptner.jwt.service.RefreshTokenService;
import com.fastcampus.aptner.jwt.util.JwtTokenizer;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.dto.HttpResponse;
import com.fastcampus.aptner.member.dto.reqeust.LoginMemberResponse;
import com.fastcampus.aptner.member.service.FindMemberRoleServiceImpl;
import com.fastcampus.aptner.member.service.loginMemberService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "JWT 리프레시 토큰 발행(사용자, 관리자)", description = "JWT 토큰 발행")
@RequiredArgsConstructor
@RequestMapping("/api/refresh-token")
@RestController
public class RefreshTokenController {

    private final JwtTokenizer jwtTokenizer;
    private final loginMemberService memberService;
    private final RefreshTokenService refreshTokenService;
    private final FindMemberRoleServiceImpl memberRoleService;
    private final FindApartmentService apartmentService;

    /*
        1. 전달받은 유저의 아이디로 유저가 존재하는지 확인한다.
        2. RefreshToken 이 유효한지 체크한다.
        3. AccessToken 을 발급하여 기존 RefreshToken 과 함께 응답한다.
    */
    @Operation(
            summary = "JWT 리프레시 토큰 발행 API",
            description = "새로운 액세스 토큰, 리프레시 토큰을 발행한다. \n\n +" +
                    "        1. 전달받은 유저의 아이디로 유저가 존재하는지 확인한다.\n\n" +
                    "        2. 리프레시 토큰이 유효한지 체크한다.\n\n" +
                    "        3. 액세스 토큰을 발급하여 기존 리프레시 토큰과 함께 응답한다."
    )
    @PostMapping("/publish")
    public ResponseEntity<?> requestRefresh(@RequestBody RefreshTokenRequest request) {
        TokenStorage refreshToken = refreshTokenService.findRefreshToken(request.getRefreshToken()).orElseThrow(() -> new IllegalArgumentException("Refresh token not found"));
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken.getRefreshToken());

        Long memberId = Long.valueOf((Integer) claims.get("memberId"));

        // TODO: 회원 정보  예외 처리 수정하기.
        Member member = memberService.findMemberById(memberId)
                .orElseThrow(() -> new CustomAPIException("회원이 존재하지 않습니다."));

        String username = claims.getSubject();
        Long apartmentId = (Long) claims.get("apartmentId");

        // 회원 아이디, 아파트로 권한 찾기
        String apartmentName = apartmentService.findApartmentById(apartmentId).getName();
        String memberRole = memberRoleService.getMemberRole(member.getMemberId(), apartmentId).toString();

        String accessToken = jwtTokenizer.createAccessToken(memberId, username, memberRole, apartmentName, apartmentId);

        LoginMemberResponse loginResponse = LoginMemberResponse.builder()
                .accessToken(accessToken)
                .refreshToken(request.getRefreshToken())
                .memberId(member.getMemberId())
                .nickname(member.getUsername())
                .build();

        return new ResponseEntity<>(new HttpResponse<>(1, "리프레시 토큰이 재발급 되었습니다.", loginResponse), HttpStatus.OK);
    }
}
