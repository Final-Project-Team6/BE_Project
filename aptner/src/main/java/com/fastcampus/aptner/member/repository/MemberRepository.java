package com.fastcampus.aptner.member.repository;

import com.fastcampus.aptner.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByPhone(String phone);
    boolean existsByNickname(String nickname);
    boolean existsByUsername(String username);

    Optional<Member> findMemberById(Long id);
    Optional<Member> findMemberByUsername(String username);
}
