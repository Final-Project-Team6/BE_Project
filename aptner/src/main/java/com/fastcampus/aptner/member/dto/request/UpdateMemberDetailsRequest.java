package com.fastcampus.aptner.member.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateMemberDetailsRequest {

    @NotEmpty
    @Pattern(regexp = "(?=\\S+$)[\\w가-힣]{2,10}",
            message = "닉네임에는 공백이나 특수문자를 사용할 수 없으며, 2자 이상 10자 이하여야 합니다.")
    private String nickname;
}
