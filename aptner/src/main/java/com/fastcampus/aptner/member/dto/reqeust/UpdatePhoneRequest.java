package com.fastcampus.aptner.member.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "회원 휴대전화번호 수정하기 DTO", description = "회원 휴대전화번호 수정하기")
@Getter
public class UpdatePhoneRequest {

    @Schema(description = "회원 휴대전화번호 요청 Body")
    private String phone;
}
