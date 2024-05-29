package com.fastcampus.aptner.member.repository;

public interface MemberHomeRepositoryCustom {
    boolean existsByMemberIdAndApartmentId(Long memberId, Long apartmentId);
}
