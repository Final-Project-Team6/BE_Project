package com.fastcampus.aptner.member.controller;

import com.fastcampus.aptner.member.dto.request.JoinMemberRequest;
import com.fastcampus.aptner.member.dto.response.HttpMemberResponse;
import com.fastcampus.aptner.member.service.JoinMemberService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/member/join")
@RestControllerAdvice
public class JoinMemberController {

    JoinMemberService joinMemberService;

    /**
     * @param username 중복 여부 확인
     * @return Member 존재 유무를 반환
     */
    @GetMapping("/v1/check-username")
    public HttpMemberResponse<String> isMemberUsernameAvailable(@RequestParam(name = "username") String username) {
        joinMemberService.checkUserNameDuplication(username);
        return HttpMemberResponse.of(HttpStatus.OK, "사용 가능한 아이디 입니다.", null);
    }

    /**
     * @param nickname 중복 여부 확인
     * @return Member 존재 유무를 반환
     */
    @GetMapping("/v1/check-nickname")
    public HttpMemberResponse<String> isNickNameAvailable(@RequestParam(name = "nickname") String nickname) {
        joinMemberService.checkNicknameDuplication(nickname);
        return HttpMemberResponse.of(HttpStatus.OK, "사용 가능한 닉네임 입니다.", null);
    }

    @PostMapping("/v1/save")
    public HttpMemberResponse<String> signUpMember(@RequestBody @Valid JoinMemberRequest request) {
        joinMemberService.signUpMember(request);

        // TODO : joinMemberResponse 반환값 확인하기
        return HttpMemberResponse.of(HttpStatus.OK, "성공적으로 회원을 등록했습니다.", null);
    }







}
