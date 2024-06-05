package com.fastcampus.aptner.member.repository;

import com.fastcampus.aptner.apartment.domain.Home;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.domain.MemberHome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberHomeRepository extends JpaRepository<MemberHome, Long>, MemberHomeRepositoryCustom {
    Optional<MemberHome> findByMemberIdAndHomeId(Member memberId, Home homeId);
    List<MemberHome> findAllByMemberId(Member member);
    Optional<MemberHome> findByMemberIdAndMemberHomeId(Member member, Long memberHomeId);
}
