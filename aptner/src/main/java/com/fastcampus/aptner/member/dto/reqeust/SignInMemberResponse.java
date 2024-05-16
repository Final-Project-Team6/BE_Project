package com.fastcampus.aptner.member.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInMemberResponse {
    private String accessToken;
    private String refreshToken;

    private Long memberId;
    private String nickname;
}
