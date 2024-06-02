package com.fastcampus.aptner.member.controller;

import com.fastcampus.aptner.jwt.dto.RefreshTokenRequest;
import com.fastcampus.aptner.jwt.service.RefreshTokenService;
import com.fastcampus.aptner.jwt.util.JwtTokenizer;
import com.fastcampus.aptner.member.dto.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원 로그아웃(사용자)", description = "회원 로그아웃")
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class LogoutMemberController {

    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenService refreshTokenService;

    @Operation(
            summary = "회원 로그아웃 API",
            description = "refreshToken: 리프레시 토큰(필수)"
    )
    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        // 리프레시 토큰에서 멤버 ID를 추출하여 삭제합니다.
        Long memberId = jwtTokenizer.getMemberIdFromToken(refreshTokenRequest.getRefreshToken());
        refreshTokenService.deleteRefreshToken(memberId);
        return new ResponseEntity<>(new HttpResponse<>(1, "로그아웃 되었습니다.", null), HttpStatus.OK);
    }
}