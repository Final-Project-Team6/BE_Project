package com.fastcampus.aptner.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberStatus {
    ACTIVATE("활성화"), DEACTIVATE("비활성화");

    private final String MemberStatus;

}
