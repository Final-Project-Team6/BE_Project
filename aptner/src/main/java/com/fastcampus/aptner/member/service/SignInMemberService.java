package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.member.domain.Member;

import java.util.Optional;

public interface SignInMemberService {

    Member findByUsername(String username);
    Optional<Member> findMemberById(Long memberId);

}
