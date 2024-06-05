package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.member.dto.MemberHomeDTO;

import java.util.List;

public interface MemberHomeService {
    Boolean existsByMemberIdAndApartmentId(Long memberId, Long apartmentId);
    List<MemberHomeDTO> findAllHomesByMemberId(Long memberId);
    List<MemberHomeDTO> findAllHomesByMemberIdAndApartmentId(Long memberId, Long apartmentId);
    MemberHomeDTO findHomeByMemberIdAndMemberHomeId(Long memberId, Long memberHomeId);  // 추가된 메서드
}
