package com.fastcampus.aptner.member.controller;

import com.fastcampus.aptner.member.dto.HttpResponse;
import com.fastcampus.aptner.member.dto.reqeust.JoinMemberRequest;
import com.fastcampus.aptner.member.service.JoinMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 가입(사용자)", description = "회원 등록, 휴대전화번호 검증, 닉네임 검증, 아이디 검증")
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class JoinMemberController {

    private final JoinMemberService memberService;

    @Operation(
            summary = "회원 가입 API",
            description =
                    "termsService: 서비스 이용 약관 동의(필수) \n\n" +
                    "privateInformationCollection: 개인 정보 수집 동의(필수)\n\n" +
                    "snsMarketingInformationReceive: SNS 마케팅 정보 수신 동의(선택)\n\n" +
                    "fullName: 회원 이름\n\n" +
                    "birthFirst: 회원 주민등록번호 앞자리(본인인증없이 가입된 회원은 선택입니다.)\n\n" +
                    "gender: 회원 성별(본인인증없이 가입된 회원은 선택입니다.)\n\n" +
                    "phoneCarrier: 회원 통신사(본인인증없이 가입된 회원은 선택입니다.)\n\n" +
                    "phone: 회원 휴대전화번호(필수)\n\n" +
                    "username: 회원 아이디(필수)\n\n" +
                    "password: 회원 비밀번호(필수)\n\n" +
                    "nickname: 회원 닉네임(필수)\n\n" +
                    "dong: 회원 아파트 동(필수)\n\n" +
                    "ho: 회원 아파트 호(필수)\n\n" +
                    "apartmentName: 회원 아파트 이름(필수)"
    )
    @PostMapping("/join")
    public ResponseEntity<?> joinMember(@RequestBody @Valid JoinMemberRequest request, BindingResult result) {
        memberService.joinMember(request);
        return new ResponseEntity<>(new HttpResponse<>(1, "회원 가입에 성공했습니다.", null), HttpStatus.CREATED);
    }


    // TODO: 휴대전화번호 인증을 위한 API 검증이 필요하다.
    @Operation(
            summary = "회원 휴대전화번호 중복 여부(검증) API",
            description = "phone: 회원 휴대전화번호(필수)"
    )
    @GetMapping("/check-phone")
    public ResponseEntity<?> isPhoneAvailable(@RequestParam(name = "phone") String phone) {
        memberService.checkMemberPhoneDuplication(phone);
        return new ResponseEntity<>(new HttpResponse<>(1, "사용 가능한 휴대전화번호 입니다.", null), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 닉네임 중복 여부(검증) API",
            description = "nickname: 회원 닉네임(필수)"
    )
    @GetMapping("/check-nickname")
    public ResponseEntity<?> isNicknameAvailable(@RequestParam(name = "nickname") String nickname) {
        memberService.checkMemberNickNameDuplication(nickname);
        return new ResponseEntity<>(new HttpResponse<>(1, "사용 가능한 닉네임 입니다.", null), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 아이디 중복 여부(검증) API",
            description = "username: 회원 아이디(필수)"
    )
    @GetMapping("/check-username")
    public ResponseEntity<?> isUsernameAvailable(@RequestParam(name = "username") String username) {
        memberService.checkMemberNickNameDuplication(username);
        return new ResponseEntity<>(new HttpResponse<>(1, "사용 가능한 아이디 입니다.", null), HttpStatus.OK);
    }
}
