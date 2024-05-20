package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.global.handler.exception.CustomDataNotFoundException;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UpdateMemberServiceImpl implements UpdateMemberService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void updateByAuthenticationStatus(Long memberId, Boolean authenticationStatus) {
        int updatedRows = memberRepository.updateAuthenticationStatusById(memberId, authenticationStatus);
        if (updatedRows == 0) {
            throw new CustomDataNotFoundException("회원이 존재하지 않습니다.");
        }
    }

    @Transactional
    @Override
    public void updateByNickname(Long memberId, String nickname) {
        int updatedRows = memberRepository.updateNicknameById(memberId, nickname);
        if (updatedRows == 0) {
            throw new CustomDataNotFoundException("회원이 존재하지 않습니다.");
        }
    }
}
