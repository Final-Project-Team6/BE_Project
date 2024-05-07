package com.fastcampus.aptner.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateMemberPasswordRequest {

    @NotEmpty
    private String beforePassword;

    @NotBlank(message = "새로운 비밀번호를 입력하세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 조합하여 작성해야 합니다.")
    private String afterPassword;
}
