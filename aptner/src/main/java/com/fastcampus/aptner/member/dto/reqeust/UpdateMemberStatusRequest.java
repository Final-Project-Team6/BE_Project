package com.fastcampus.aptner.member.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "회원 활성화 수정하기 DTO", description = "회원 활성화 수정하기")
@Getter
public class UpdateMemberStatusRequest {

    @Schema(description = "회원 활성화 수정 요청 Body: ACTIVATE, DEACTIVATE")
    private String status;
}
