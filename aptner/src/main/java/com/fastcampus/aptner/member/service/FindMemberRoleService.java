package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.member.domain.RoleName;

public interface FindMemberRoleService {
    RoleName getMemberRole(Long memberId, Long apartmentId);
}
