package com.fastcampus.aptner.member.controller;

import com.fastcampus.aptner.apartment.service.FindApartmentService;
import com.fastcampus.aptner.jwt.domain.TokenStorage;
import com.fastcampus.aptner.jwt.service.RefreshTokenService;
import com.fastcampus.aptner.jwt.util.JwtTokenizer;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.dto.HttpResponse;
import com.fastcampus.aptner.member.dto.reqeust.LoginMemberRequest;
import com.fastcampus.aptner.member.dto.reqeust.LoginMemberResponse;
import com.fastcampus.aptner.member.service.FindMemberRoleServiceImpl;
import com.fastcampus.aptner.member.service.loginMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/login")
@RestController
public class LoginMemberController {

    private final JwtTokenizer jwtTokenizer;
    private final loginMemberService memberService;
    private final RefreshTokenService refreshTokenService;
    private final FindMemberRoleServiceImpl memberRoleService;
    private final FindApartmentService apartmentService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/member")
    public ResponseEntity<?> signIn(@RequestBody LoginMemberRequest request, BindingResult bindingResult) {
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

        // TODO: RefreshToken 을 MySQL 에 저장. -> Redis 으로 저장 필요하다.
        TokenStorage refreshTokenEntity = TokenStorage.builder()
                .refreshToken(refreshToken)
                .memberId(member.getMemberId())
                .build();

        refreshTokenService.addRefreshToken(refreshTokenEntity);

        LoginMemberResponse loginResponse = LoginMemberResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberId(member.getMemberId())
                .nickname(member.getUsername())
                .build();

        return new ResponseEntity<>(new HttpResponse<>(1, "로그인 성공 입니다.", loginResponse), HttpStatus.OK);
    }
}
