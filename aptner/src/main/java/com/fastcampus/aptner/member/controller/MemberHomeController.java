package com.fastcampus.aptner.member.controller;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.domain.MemberHome;
import com.fastcampus.aptner.member.dto.HttpResponse;
import com.fastcampus.aptner.member.dto.MemberHomeDTO;
import com.fastcampus.aptner.member.service.MemberHomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "회원 자택 조회(모든 사용자)", description = "회원 자택 조회")
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberHomeController {

    private final MemberHomeService memberHomeService;

    @Operation(
            summary = "회원 모든 자택 조회 API",
            description = "USER, MANAGER, ADMIN 권한 필수"
    )
    @GetMapping("/home")
    public ResponseEntity<?> findAllMemberHome(@AuthenticationPrincipal JWTMemberInfoDTO memberToken) {
        List<MemberHomeDTO> allHomesByMemberId = memberHomeService.findAllHomesByMemberId(memberToken.getMemberId());

        return new ResponseEntity<>(new HttpResponse<>(1, "회원의 모든 자택입니다.", allHomesByMemberId), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 단건 자택 조회 API",
            description = "USER, MANAGER, ADMIN 권한 필수\n\n" +
                    "memberHomeId: 회원 자택 고유 식별 번호(필수)"
    )
    @GetMapping("/home/{memberHomeId}")
    public ResponseEntity<?> findHomeByMemberHomeId(@AuthenticationPrincipal JWTMemberInfoDTO memberToken,
                                                    @PathVariable Long memberHomeId) {
        MemberHomeDTO memberHome = memberHomeService.findHomeByMemberIdAndMemberHomeId(memberToken.getMemberId(), memberHomeId);
        return new ResponseEntity<>(new HttpResponse<>(1, "회원의 자택입니다.", memberHome), HttpStatus.OK);
    }
}
