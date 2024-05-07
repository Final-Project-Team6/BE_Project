package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.member.dto.request.JoinMemberRequest;

public interface JoinMemberService {
    void checkUserNameDuplication(String username);
    void checkNicknameDuplication(String nickname);
    void signUpMember(JoinMemberRequest request);
}
