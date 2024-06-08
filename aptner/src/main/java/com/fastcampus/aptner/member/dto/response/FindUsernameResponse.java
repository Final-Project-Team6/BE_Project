package com.fastcampus.aptner.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FindUsernameResponse {

    private String fullName;
    private String username;

    @Builder

    public FindUsernameResponse(String fullName, String username) {
        this.fullName = fullName;
        this.username = username;
    }
}
