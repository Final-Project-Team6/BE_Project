package com.fastcampus.aptner.member.service;

public interface UpdateMemberService {

    void updateByAuthenticationStatus(Long memberId, Boolean authenticationStatus);
    void updateByNickname(Long memberId, String nickname);

}
