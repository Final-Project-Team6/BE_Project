package com.fastcampus.aptner.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberInformationResponse {

    private String profileImage;
    private String fullName;
    private String username;
    private Boolean authenticationStatus;
    private String phone;
    private String nickname;

    @Builder
    public MemberInformationResponse(String profileImage, String fullName, String username, Boolean authenticationStatus, String phone, String nickname) {
        this.profileImage = profileImage;
        this.fullName = fullName;
        this.username = username;
        this.authenticationStatus = authenticationStatus;
        this.phone = phone;
        this.nickname = nickname;
    }
}
