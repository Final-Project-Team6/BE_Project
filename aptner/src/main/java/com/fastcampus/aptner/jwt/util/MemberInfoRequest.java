package com.fastcampus.aptner.jwt.util;

import lombok.Data;

@Data
public class MemberInfoRequest {
    private Long memberId;
    private String username;
    private String apartmentName;
}
