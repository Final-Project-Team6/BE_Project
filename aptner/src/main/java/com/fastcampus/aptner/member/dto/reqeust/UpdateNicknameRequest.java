package com.fastcampus.aptner.member.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(name = "회원 닉네임 수정하기 DTO", description = "회원 닉네임 수정하기")
@Getter
@RequiredArgsConstructor
public class UpdateNicknameRequest {

    @Schema(description = "회원 닉네임 요청 Body")
    @NotBlank(message = "닉네임: 필수 정보입니다.")
    @Pattern(regexp = "(?=\\S+$)[\\w가-힣a-zA-Z]{2,16}"
            , message = "닉네임: 공백이나 특수문자를 사용할 수 없으며, 2자 이상 16자 이하여야 합니다.")
    @Size(min = 2, max = 16)
    private String nickname;
}
