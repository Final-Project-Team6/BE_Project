package com.fastcampus.aptner.member.controller;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.service.FindApartmentService;
import com.fastcampus.aptner.jwt.service.RefreshTokenService;
import com.fastcampus.aptner.jwt.util.JwtTokenizer;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.dto.HttpResponse;
import com.fastcampus.aptner.member.dto.reqeust.LoginMemberRequest;
import com.fastcampus.aptner.member.dto.response.LoginMemberResponse;
import com.fastcampus.aptner.member.service.FindMemberRoleServiceImpl;
import com.fastcampus.aptner.member.service.LoginMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 로그인(사용자)", description = "회원 로그인")
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class LoginMemberController {

    private final JwtTokenizer jwtTokenizer;
    private final LoginMemberService memberService;
    private final RefreshTokenService refreshTokenService;
    private final FindMemberRoleServiceImpl memberRoleService;
    private final FindApartmentService apartmentService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Operation(
            summary = "회원 로그인 API",
            description = "username: 회원 아이디(필수)\n\n" +
                    "password: 회원 비밀번호(필수)\n\n" +
                    "apartmentId: 회원 아파트 아이디(필수)\n\n"
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginMemberRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new HttpResponse<>(-1, "잘못된 요청 입니다.", null), HttpStatus.BAD_REQUEST);
        }

        Member member = memberService.findByUsername(request.getUsername());
        if (!bCryptPasswordEncoder.matches(request.getPassword(), member.getPassword())) {
            return new ResponseEntity<>(new HttpResponse<>(-1, "유효하지 않은 인증 입니다.", null), HttpStatus.UNAUTHORIZED);
        }

        // 회원 아이디와 아파트 ID로 권한 찾기
        Apartment apartment = apartmentService.findApartmentById(request.getApartmentId());
        String memberRole = memberRoleService.getMemberRole(member.getMemberId(), apartment.getApartmentId()).toString();

        // JWT 토큰 생성
        String accessToken = jwtTokenizer.createAccessToken(member.getMemberId(), member.getUsername(), memberRole, apartment.getName(), apartment.getApartmentId());
        String refreshToken = jwtTokenizer.createRefreshToken(member.getMemberId(), member.getUsername(), memberRole, apartment.getName(), apartment.getApartmentId());

        // 리프레시 토큰을 레디스에 저장
        refreshTokenService.addRefreshToken(member.getMemberId(), refreshToken);

        LoginMemberResponse loginResponse = LoginMemberResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .build();

        return new ResponseEntity<>(new HttpResponse<>(1, "로그인 성공 입니다.", loginResponse), HttpStatus.OK);
    }
}