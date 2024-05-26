package com.fastcampus.aptner.member.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "회원 로그인 요청하기",description = "회원 로그인 요청하기")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginMemberRequest {

    @Schema(description = "username: 회원 아이디")
    @NotBlank(message = "아이디: 필수 정보입니다.")
    @Pattern(regexp = "^[a-z0-9_-]{5,20}$", message = "아이디: 5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.")
    @Size(min = 5, max = 20)
    private String username;

    @Schema(description = "password: 회원 비밀번호")
    @NotBlank(message = "비밀번호: 필수 정보입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}"
            , message = "비밀번호: 8~20자 영문 대소문자, 숫자, 특수문자를 조합하여 작성해야 합니다.")
    @Size(min = 8, max = 20)
    private String password;

    // TODO: 단일 아파트 기준으로 생성하기 때문에, 클라이언트로부터 아파트 정보를 받지 못한다.
    @Schema(description = "apartmentId: 회원 아파트 고유 식별 번호")
    private Long apartmentId;
}
