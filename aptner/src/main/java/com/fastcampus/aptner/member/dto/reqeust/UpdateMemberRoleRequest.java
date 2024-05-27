package com.fastcampus.aptner.member.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "회원 권한 수정하기 DTO", description = "회원 권한 수정하기")
@Getter
public class UpdateMemberRoleRequest {

    @Schema(description = "회원 권한 수정 요청 Body: USER, MANAGER, ADMIN")
    private String roleName;
}
