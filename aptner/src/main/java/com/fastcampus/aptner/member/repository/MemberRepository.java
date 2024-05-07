package com.fastcampus.aptner.member.repository;

import com.fastcampus.aptner.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    boolean existsMemberByUsername(String username);
    boolean existsMemberByNickname(String nickname);


}

