package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.member.dto.SubscriptionDTO;
import com.fastcampus.aptner.member.dto.reqeust.InsertMemberHomeRequest;
import com.fastcampus.aptner.member.dto.reqeust.UpdateMemberProfileRequest;

public interface UpdateMemberService {

    void updateAuthenticationStatusById(Long memberId, Boolean authenticationStatus);
    void updateNicknameById(Long memberId, String nickname);
    void updatePhoneById(Long memberId, String phone);
    void updatePasswordById(Long memberId, String password);
    void updateMemberRoleByMemberIdAndApartmentId(Long memberId, Long apartmentId, String roleName);
    void insertMemberHomeByMemberIdAndApartmentId(Long memberId, InsertMemberHomeRequest request);
    void deleteMemberHomeByMemberIdAndHomeId(Long memberId, Long homeId);
    void updateMemberHomeDongHo(Long memberId, Long homeId, String newDong, String newHo);
    void updateMemberStatus(Long memberId, String status);
    void updateMemberSubscription(Long memberId, SubscriptionDTO subscriptionDTO);
    SubscriptionDTO getMemberSubscription(Long memberId);
    void updateMemberProfileImage(Long memberId, String profileImage);

}
