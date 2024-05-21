package com.fastcampus.aptner.member.repository;

import com.fastcampus.aptner.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByPhone(String phone);
    boolean existsByNickname(String nickname);
    boolean existsByUsername(String username);

    Optional<Member> findMemberByMemberId(Long memberId);
    Optional<Member> findMemberByUsername(String username);


    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.authenticationStatus = :authenticationStatus WHERE m.memberId = :memberId")
    int updateAuthenticationStatusById(@Param("memberId") Long memberId, @Param("authenticationStatus") Boolean authenticationStatus);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.nickname = :nickname WHERE m.memberId = :memberId")
    int updateNicknameById(@Param("memberId") Long memberId, @Param("nickname") String nickname);}
