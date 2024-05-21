package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberCommonServiceImpl implements MemberCommonService {

    private final MemberRepository memberRepository;

    @Override
    public Member getUserByToken(JWTMemberInfoDTO dto) {
        return memberRepository.findById(1L).orElse(null);
    }
}
