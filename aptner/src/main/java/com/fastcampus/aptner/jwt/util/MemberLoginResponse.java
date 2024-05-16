package com.fastcampus.aptner.jwt.util;

import lombok.Data;

@Data
public class MemberLoginResponse {
    private Long memberId;
    private String username;
    private String apartmentName;
    private String roleName;
}
