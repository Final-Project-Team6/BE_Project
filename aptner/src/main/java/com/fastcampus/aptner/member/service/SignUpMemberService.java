package com.fastcampus.aptner.member.service;


import com.fastcampus.aptner.member.dto.reqeust.SignUpMemberRequest;

public interface SignUpMemberService {
    void checkMemberPhoneDuplication(String phone);
    void checkMemberNickNameDuplication(String nickname);
    void checkMemberUsernameDuplication(String username);
    void signUpMember(SignUpMemberRequest request);

}
