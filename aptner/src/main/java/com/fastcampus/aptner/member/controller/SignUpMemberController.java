package com.fastcampus.aptner.member.controller;

import com.fastcampus.aptner.member.dto.HttpResponse;
import com.fastcampus.aptner.member.dto.reqeust.SignUpMemberRequest;
import com.fastcampus.aptner.member.service.SignUpMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class SignUpMemberController {

    private final SignUpMemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUpMember(@RequestBody SignUpMemberRequest request) {
        memberService.signUpMember(request);
        return new ResponseEntity<>(new HttpResponse<>(1, "회원 가입에 성공했습니다.", null), HttpStatus.CREATED);
    }


    // TODO: 휴대전화번호 인증을 위한 API 검증이 필요하다.
    @GetMapping("/check-phone")
    public ResponseEntity<?> isPhoneAvailable(@RequestParam(name = "phone") String phone) {
        memberService.checkMemberPhoneDuplication(phone);
        return new ResponseEntity<>(new HttpResponse<>(1, "사용 가능한 휴대전화번호 입니다.", null), HttpStatus.OK);
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<?> isNicknameAvailable(@RequestParam(name = "nickname") String nickname) {
        memberService.checkMemberNickNameDuplication(nickname);
        return new ResponseEntity<>(new HttpResponse<>(1, "사용 가능한 닉네임 입니다.", null), HttpStatus.OK);
    }

    @GetMapping("/check-username")
    public ResponseEntity<?> isUsernameAvailable(@RequestParam(name = "username") String username) {
        memberService.checkMemberNickNameDuplication(username);
        return new ResponseEntity<>(new HttpResponse<>(1, "사용 가능한 아이디 입니다.", null), HttpStatus.OK);
    }
}
