package com.fastcampus.aptner.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {
    // TODO: 권한이 변경될 수 있다.
    RESIDENT("입주민"), MANAGER("관계자"), ADMIN("대표자");

    private final String roleName;
}
