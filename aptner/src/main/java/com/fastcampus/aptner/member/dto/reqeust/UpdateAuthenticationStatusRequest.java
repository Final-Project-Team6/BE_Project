package com.fastcampus.aptner.member.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(name = "회원 인증상태 수정하기 DTO", description = "회원 인증상태 수정하기")
@Getter
@RequiredArgsConstructor
public class UpdateAuthenticationStatusRequest {

    @Schema(description = "회원 인증상태 요청 Body")
    private Boolean authenticationStatus;
}
