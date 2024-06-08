package com.fastcampus.aptner.member.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(name = "회원 비밀번호 찾은 후 수정하기", description = "회원 비밀번호 수정하기")
@RequiredArgsConstructor
@Getter
public class FindPasswordRequest {

    @Schema(description = "회원 고유식별번호")
    private Long memberId;

    @Schema(description = "회원 비밀번호 요청 Body")
    @NotBlank(message = "비밀번호: 필수 정보입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}"
            , message = "비밀번호: 8~20자 영문 대소문자, 숫자, 특수문자를 조합하여 작성해야 합니다.")
    @Size(min = 8, max = 20)
    private String password;

    @Schema(description = "UUID")
    private String memberIdUUID;
}
