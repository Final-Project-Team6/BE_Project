package com.fastcampus.aptner.jwt.util;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class JWTMemberInfoDTO {
    private Long memberId;
    private String username;
    private Long apartmentId;
    private String apartmentName;
    private String roleName;
}
