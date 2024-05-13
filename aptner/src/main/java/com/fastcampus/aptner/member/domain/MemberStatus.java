package com.fastcampus.aptner.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberStatus {
    // TODO: 상태는 변경될 수 있다.
    ACTIVATE("활성화"), DEACTIVATE("비활성화");

    private final String MemberStatus;

}
