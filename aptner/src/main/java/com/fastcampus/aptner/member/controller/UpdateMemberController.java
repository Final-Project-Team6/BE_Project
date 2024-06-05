package com.fastcampus.aptner.member.controller;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.dto.HttpResponse;
import com.fastcampus.aptner.member.dto.SubscriptionDTO;
import com.fastcampus.aptner.member.dto.reqeust.*;
import com.fastcampus.aptner.member.dto.response.MemberInformationResponse;
import com.fastcampus.aptner.member.service.UpdateMemberServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 정보 수정(사용자)", description = "회원 정보 수정")
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class UpdateMemberController {

    // TODO: 예외 및 유효성 검사 처리가 필요하다. -> 수정 시점
    private final UpdateMemberServiceImpl memberService;

    @Operation(
            summary = "회원 인증상태 수정 API",
            description = "USER, MANAGER, ADMIN 권한 필수\n\n" +
                    "authenticationStatus: 회원 인증 상태 여부(필수)"
    )
    @PatchMapping("/authentication-status")
    public ResponseEntity<?> updateMemberStatus(@AuthenticationPrincipal JWTMemberInfoDTO request,
                                                @RequestBody @Valid UpdateAuthenticationStatusRequest statusRequest) {
        memberService.updateAuthenticationStatusById(request.getMemberId(), statusRequest.getAuthenticationStatus());

        return new ResponseEntity<>(new HttpResponse<>(1, "회원 인증 상태를 변경했습니다.", null), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 닉네임 수정 API",
            description = "USER, MANAGER, ADMIN 권한 필수\n\n" +
                    "nickname: 회원 닉네임 수정(필수)"
    )
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @PatchMapping("/nickname")
    public ResponseEntity<?> updateMemberNickname(@AuthenticationPrincipal JWTMemberInfoDTO request,
                                                  @RequestBody @Valid UpdateNicknameRequest nicknameRequest,
                                                  BindingResult bindingResult) {

        System.out.println(request.getMemberId());
        memberService.updateNicknameById(request.getMemberId(), nicknameRequest.getNickname());

        return new ResponseEntity<>(new HttpResponse<>(1, "회원 닉네임을 변경했습니다.", null), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 휴대전화번호 수정 API",
            description = "USER, MANAGER, ADMIN 권한 필수\n\n" +
                    "phone: 회원 휴대전화번호 수정(필수)"
    )
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @PatchMapping("/phone")
    public ResponseEntity<?> updateMemberPhone(@AuthenticationPrincipal JWTMemberInfoDTO request,
                                               @RequestBody @Valid UpdatePhoneRequest phoneRequest,
                                               BindingResult bindingResult) {

        System.out.println(request.getMemberId());
        memberService.updatePhoneById(request.getMemberId(), phoneRequest.getPhone());

        return new ResponseEntity<>(new HttpResponse<>(1, "회원 휴대전화번호를 변경했습니다.", null), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 비밀번호 수정 API",
            description = "USER, MANAGER, ADMIN 권한 필수\n\n" +
                    "password: 회원 비밀번호 수정(필수)"
    )
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @PatchMapping("/password")
    public ResponseEntity<?> updateMemberPassword(@AuthenticationPrincipal JWTMemberInfoDTO request,
                                                  @RequestBody @Valid UpdatePasswordRequest passwordRequest,
                                                  BindingResult bindingResult) {

        System.out.println(request.getMemberId());
        memberService.updatePasswordById(request.getMemberId(), passwordRequest.getPassword());

        return new ResponseEntity<>(new HttpResponse<>(1, "회원 휴대전화번호를 변경했습니다.", null), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 권한 수정 API",
            description = "USER, MANAGER, ADMIN 권한 필수\n\n" +
                    "roleName: 회원 권한 수정(필수)"
    )
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @PatchMapping("/role")
    public ResponseEntity<?> updateMemberRole(@AuthenticationPrincipal JWTMemberInfoDTO request,
                                              @RequestBody @Valid UpdateMemberRoleRequest roleRequest,
                                              BindingResult bindingResult) {

        System.out.println(request.getMemberId());
        memberService.updateMemberRoleByMemberIdAndApartmentId(request.getMemberId(), request.getApartmentId(), roleRequest.getRoleName());

        return new ResponseEntity<>(new HttpResponse<>(1, "회원 권한을 변경했습니다.", null), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 자택 추가 API",
            description = "USER, MANAGER, ADMIN 권한 필수\n\n" +
                    "dong: 회원 아파트 동 추가(필수)\n\n" +
                    "ho: 회원 아파트 호 추가(필수)"
    )
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @PostMapping("/home")
    public ResponseEntity<?> insertMemberHome(@AuthenticationPrincipal JWTMemberInfoDTO request,
                                              @RequestBody @Valid InsertMemberHomeRequest homeRequest,
                                              BindingResult bindingResult) {

        System.out.println(request.getMemberId());
        System.out.println("homeRequest.getApartmentId() = " + homeRequest.getApartmentId());
        memberService.insertMemberHomeByMemberIdAndApartmentId(request.getMemberId(), homeRequest);

        return new ResponseEntity<>(new HttpResponse<>(1, "회원 자택을 추가했습니다.", null), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 자택 삭제 API",
            description = "USER, MANAGER, ADMIN 권한 필수\n\n" +
                    "homeId: 자택 고유 식별 번호"
    )
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @DeleteMapping("/home/{homeId}")
    public ResponseEntity<?> deleteMemberHome(@AuthenticationPrincipal JWTMemberInfoDTO request,
                                              @PathVariable(name = "homeId") Long homeId) {

        System.out.println(request.getMemberId());
        memberService.deleteMemberHomeByMemberIdAndHomeId(request.getMemberId(), homeId);

        return new ResponseEntity<>(new HttpResponse<>(1, "회원 자택을 삭제했습니다.", null), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 자택 수정 API",
            description = "USER, MANAGER, ADMIN 권한 필수\n\n" +
                    "homeId: 자택 고유 식별 번호\n\n" +
                    "newDong: 아파트 동\n\n" +
                    "newHo: 아파트 호"
    )
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @PutMapping("/home/{homeId}")
    public ResponseEntity<?> updateMemberHome(@AuthenticationPrincipal JWTMemberInfoDTO request,
                                              @PathVariable(name = "homeId") Long homeId,
                                              @RequestParam(name = "dong") String dong,
                                              @RequestParam(name = "ho") String ho) {

        System.out.println(request.getMemberId());
        memberService.updateMemberHomeDongHo(request.getMemberId(), homeId, dong, ho);

        return new ResponseEntity<>(new HttpResponse<>(1, "회원 자택을 수정했습니다.", null), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 활성화 상태 변경 API",
            description = "USER, MANAGER, ADMIN 권한 필수\n\n" +
                    "status: 회원 활성화 상태(ACTIVATE, DEACTIVATE)\n\n"
    )
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @PatchMapping("/status")
    public ResponseEntity<?> updateMemberStatus(@AuthenticationPrincipal JWTMemberInfoDTO request,
                                                @RequestBody UpdateMemberStatusRequest status) {

        System.out.println(request.getMemberId());
        memberService.updateMemberStatus(request.getMemberId(), status.getStatus());

        return new ResponseEntity<>(new HttpResponse<>(1, "회원 상태를 변경했습니다.", null), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 구독 정보 수정 API",
            description = "USER, MANAGER, ADMIN 권한 필수\n\n" +
                    "termsService: 서비스 이용 약관 동의(필수)\n\n" +
                    "privateInformationCollection: 개인 정보 수집 동의(필수)" +
                    "snsMarketingInformationReceive: SNS 마케팅 정보 수신 동의(선택)"
    )
    @PutMapping("/subscription")
    public ResponseEntity<?> updateMemberSubscription(@AuthenticationPrincipal JWTMemberInfoDTO memberToken,
                                                      @RequestBody SubscriptionDTO subscriptionDTO) {

        memberService.updateMemberSubscription(memberToken.getMemberId(), subscriptionDTO);
        return new ResponseEntity<>(new HttpResponse<>(1, "구독 정보를 변경했습니다.", null), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 구독 정보 조회 API",
            description = "USER, MANAGER, ADMIN 권한 필수"
    )
    @GetMapping("/subscription")
    public ResponseEntity<?> getMemberSubscription(@AuthenticationPrincipal JWTMemberInfoDTO memberToken) {
        SubscriptionDTO subscriptionDTO = memberService.getMemberSubscription(memberToken.getMemberId());

        return new ResponseEntity<>(new HttpResponse<>(1, "회원의 구독 정보입니다.", subscriptionDTO), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 프로필 수정 API",
            description = "USER, MANAGER, ADMIN 권한 필수\n\n" +
                    "profileImage: 회원 이미지(필수)"
    )
    @PatchMapping("/profile")
    public ResponseEntity<?> updateMemberProfileImage(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @RequestBody UpdateMemberProfileRequest request) {
        memberService.updateMemberProfileImage(memberToken.getMemberId(), request.getProfileImage());

        return new ResponseEntity<>(new HttpResponse<>(1, "회원 이미지가 변경됐습니다.", null), HttpStatus.OK);
    }

    @Operation(
            summary = "회원 정보 조회 API",
            description = "USER, MANAGER, ADMIN 권한 필수")
    @GetMapping("/information")
    public ResponseEntity<?> getMemberInformation(@AuthenticationPrincipal JWTMemberInfoDTO memberToken) {
        MemberInformationResponse memberInformation = memberService.getMemberInformation(memberToken.getMemberId(), memberToken.getApartmentId());

        return new ResponseEntity<>(new HttpResponse<>(1, "회원의 개인 정보입니다.", memberInformation), HttpStatus.OK);
    }


}
