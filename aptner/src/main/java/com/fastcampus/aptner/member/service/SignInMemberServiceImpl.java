package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class SignInMemberServiceImpl implements SignInMemberService {

    private final MemberRepository memberRepository;


    @Transactional(readOnly = true)
    public Member findByUsername(String username) {
        return memberRepository.findMemberByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public Optional<Member> findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }
}