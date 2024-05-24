package com.fastcampus.aptner.member.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginMemberRequest {

    private String username;
    private String password;

    // TODO: 단일 아파트 기준으로 생성하기 때문에, 클라이언트로부터 아파트 정보를 받지 못한다.
    private Long apartmentId;
}
