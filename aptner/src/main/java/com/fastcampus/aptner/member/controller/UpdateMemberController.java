package com.fastcampus.aptner.member.controller;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.dto.HttpResponse;
import com.fastcampus.aptner.member.dto.reqeust.UpdateNicknameRequest;
import com.fastcampus.aptner.member.service.UpdateMemberServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원 정보 수정(사용자)", description = "회원 닉네임 수정, 회원 인증상태 수정")
@RequiredArgsConstructor
@RequestMapping("/api/member/update")
@RestController
public class UpdateMemberController {

    // TODO: 예외 및 유효성 검사 처리가 필요하다. -> 수정 시점
    private final UpdateMemberServiceImpl memberService;

    @Operation(
            summary = "회원 인증상태 수정 API",
            description = "액세스 토큰(필수, Headers(Authorization)) \n\n" +
                    "TODO: 구현 중인 API"
    )
    @PutMapping("/member-status")
    public ResponseEntity<?> updateMemberStatus(@AuthenticationPrincipal JWTMemberInfoDTO request,
                                                @RequestBody boolean authenticationStatus,
                                                BindingResult bindingResult) {
        memberService.updateByAuthenticationStatus(request.getMemberId(), authenticationStatus);

        return new ResponseEntity<>(new HttpResponse<>(1, "회원 상태를 변경했습니다.", null), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 닉네임 수정 API",
            description = "액세스 토큰(필수, Headers(Authorization)) \n\n" +
                    "nickname: 회원 닉네임 수정(필수)"
    )
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @PutMapping("/member-nickname")
    public ResponseEntity<?> updateMemberNickname(@AuthenticationPrincipal JWTMemberInfoDTO request,
                                                  @RequestBody UpdateNicknameRequest nicknameRequest,
                                                  BindingResult bindingResult) {

        System.out.println(request.getMemberId());
        memberService.updateByNickname(request.getMemberId(), nicknameRequest.getNickname());

        return new ResponseEntity<>(new HttpResponse<>(1, "회원 닉네임을 변경했습니다.", null), HttpStatus.OK);
    }
}
