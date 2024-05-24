package com.fastcampus.aptner.member.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private String username;

    @Schema(description = "password: 회원 비밀번호")
    private String password;

    // TODO: 단일 아파트 기준으로 생성하기 때문에, 클라이언트로부터 아파트 정보를 받지 못한다.
    @Schema(description = "apartmentId: 회원 아파트 고유 식별 번호")
    private Long apartmentId;
}
