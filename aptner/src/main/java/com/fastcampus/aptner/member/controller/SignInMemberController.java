package com.fastcampus.aptner.member.controller;

import com.fastcampus.aptner.apartment.service.FindApartmentService;
import com.fastcampus.aptner.jwt.domain.TokenStorage;
import com.fastcampus.aptner.jwt.dto.RefreshTokenRequest;
import com.fastcampus.aptner.jwt.service.RefreshTokenService;
import com.fastcampus.aptner.jwt.util.JwtTokenizer;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.dto.HttpResponse;
import com.fastcampus.aptner.member.dto.reqeust.SignInMemberRequest;
import com.fastcampus.aptner.member.dto.reqeust.SignInMemberResponse;
import com.fastcampus.aptner.member.service.FindMemberRoleServiceImpl;
import com.fastcampus.aptner.member.service.SignInMemberService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class SignInMemberController {

    private final JwtTokenizer jwtTokenizer;
    private final SignInMemberService memberService;
    private final RefreshTokenService refreshTokenService;
    private final FindMemberRoleServiceImpl memberRoleService;
    private final FindApartmentService apartmentService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SignInMemberRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new HttpResponse<>(-1, "잘못된 요청 입니다.", null), HttpStatus.BAD_REQUEST);
        }

        // TODO: username 이 존재하지 않으면 Exception 발생. Global Exception 으로 처리가 필요할 것.
        Member member = memberService.findByUsername(request.getUsername());
        if (!bCryptPasswordEncoder.matches(request.getPassword(), member.getPassword())) {
            return new ResponseEntity<>(new HttpResponse<>(-1, "유효하지 않은 인증 입니다.", null), HttpStatus.UNAUTHORIZED);
        }

        // 회원 아이디, 아파트로 권한 찾기
        Long apartmentId = apartmentService.findApartmentByName(request.getApartmentName()).getApartmentId();
        String memberRole = memberRoleService.getMemberRole(member.getMemberId(), apartmentService.findApartmentByName(request.getApartmentName()).getApartmentId()).toString();

        // JWT 토큰 생성하는 시점.
        String accessToken = jwtTokenizer.createAccessToken(member.getMemberId(), member.getUsername(), memberRole, request.getApartmentName(), apartmentId);
        String refreshToken = jwtTokenizer.createRefreshToken(member.getMemberId(), member.getUsername(), memberRole, apartmentId);

        // RefreshToken 을 MySQL 에 저장. -> Redis 으로 저장 필요하다.
        TokenStorage refreshTokenEntity = TokenStorage.builder()
                .refreshToken(refreshToken)
                .memberId(member.getMemberId())
                .build();

        refreshTokenService.addRefreshToken(refreshTokenEntity);

        SignInMemberResponse loginResponse = SignInMemberResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberId(member.getMemberId())
                .nickname(member.getUsername())
                .build();

        return new ResponseEntity<>(new HttpResponse<>(1, "로그인 성공 입니다.", loginResponse), HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return new ResponseEntity<>(new HttpResponse<>(1, "로그아웃 되었습니다.", null), HttpStatus.OK);
    }

    /*
        1. 전달받은 유저의 아이디로 유저가 존재하는지 확인한다.
        2. RefreshToken 이 유효한지 체크한다.
        3. AccessToken 을 발급하여 기존 RefreshToken 과 함께 응답한다.
 */
    @PostMapping("/refreshToken")
    public ResponseEntity<?> requestRefresh(@RequestBody RefreshTokenRequest refreshTokenDto) {
        TokenStorage refreshToken = refreshTokenService.findRefreshToken(refreshTokenDto.getRefreshToken()).orElseThrow(() -> new IllegalArgumentException("Refresh token not found"));
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken.getRefreshToken());

        Long memberId = Long.valueOf((Integer) claims.get("memberId"));

        // TODO: 회원 정보  예외 처리 수정하기.
        Member member = memberService.findMemberById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        String username = claims.getSubject();
        Long apartmentId = (Long) claims.get("apartmentId");

        // 회원 아이디, 아파트로 권한 찾기
        String apartmentName = apartmentService.findApartmentById(apartmentId).getName();
        String memberRole = memberRoleService.getMemberRole(member.getMemberId(), apartmentId).toString();

        String accessToken = jwtTokenizer.createAccessToken(memberId, username, memberRole, apartmentName, apartmentId);

        SignInMemberResponse loginResponse = SignInMemberResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenDto.getRefreshToken())
                .memberId(member.getMemberId())
                .nickname(member.getUsername())
                .build();

        return new ResponseEntity<>(new HttpResponse<>(1, "리프레시 토큰이 재발급 되었습니다.", loginResponse), HttpStatus.OK);
    }


}
