package com.fastcampus.aptner.member.dto.reqeust;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateNicknameRequest {

    private String nickname;
}
