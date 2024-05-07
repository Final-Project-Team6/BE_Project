package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.member.dto.response.DetailsMemberResponse;
import com.fastcampus.aptner.member.dto.request.UpdateMemberDetailsRequest;
import com.fastcampus.aptner.member.dto.request.UpdateMemberPasswordRequest;

public interface UpdateMemberService {
    DetailsMemberResponse findMemberDetails(Long id);
    void updateMemberPassword(Long id, UpdateMemberPasswordRequest response);
    void updateMemberDetails(Long id, UpdateMemberDetailsRequest response); // TODO: 닉네임만 변경되는 상태
    void deleteMember(Long id, String password);
}
