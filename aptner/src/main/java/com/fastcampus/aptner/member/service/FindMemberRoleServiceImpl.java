package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.member.domain.RoleName;
import com.fastcampus.aptner.member.repository.MemberRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FindMemberRoleServiceImpl implements FindMemberRoleService {

    private final MemberRoleRepository memberRoleRepository;


    @Transactional(readOnly = true)
    public RoleName getMemberRole(Long memberId, Long apartmentId) {
        return memberRoleRepository.findRoleByMemberAndApartment(memberId, apartmentId)
                .orElseThrow(() -> new IllegalArgumentException("회원의 권한이 존재하지 않습니다."));
    }
}
