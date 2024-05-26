package com.fastcampus.aptner.post.communication.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommunicationType {
    USER_COMMU("입주민 소통공간"),REPRESENT_COMMU("입대의 소통공간");
    private final String CommunicationType;
}
