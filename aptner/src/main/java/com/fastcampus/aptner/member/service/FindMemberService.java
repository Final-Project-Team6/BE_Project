package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.member.dto.response.FindUsernameResponse;

public interface FindMemberService {
    FindUsernameResponse getMemberByPhoneAndFullName(String phone, String fullName);
    Long getMemberByUserNameAndFullNameAndPhone(String username, String fullName, String phone);
    void modificationPassword(Long id, String password, String memberIdUUID);
}
