package com.fastcampus.aptner.member.service;


import com.fastcampus.aptner.member.dto.reqeust.JoinMemberRequest;

public interface JoinMemberService {
    void checkMemberPhoneDuplication(String phone);
    void checkMemberNickNameDuplication(String nickname);
    void checkMemberUsernameDuplication(String username);
    void joinMember(JoinMemberRequest request);

}
