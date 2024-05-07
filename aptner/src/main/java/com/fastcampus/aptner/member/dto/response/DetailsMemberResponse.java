package com.fastcampus.aptner.member.dto.response;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.domain.MemberRole;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailsMemberResponse {

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String fullName;
    private String phone;
    private MemberRole memberRole;

    public static DetailsMemberResponse from(Member member) {
        return DetailsMemberResponse.builder()
                .id(member.getId())
                .username(member.getUsername())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .fullName(member.getFullName())
                .phone(member.getPhone())
                .memberRole(member.getMemberRole())
                .build();
    }

}
