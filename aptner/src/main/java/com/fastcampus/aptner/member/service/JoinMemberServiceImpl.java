package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.global.handler.exception.DataDuplicateKeyException;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.dto.request.JoinMemberRequest;
import com.fastcampus.aptner.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class JoinMemberServiceImpl implements JoinMemberService {

    MemberRepository memberRepository;
    CryptPasswordService cryptPasswordService;

    @Override
    public void checkUserNameDuplication(String username) {
        if (memberRepository.existsMemberByUsername(username)) {
            throw new DataDuplicateKeyException("이미 존재하는 아이디 입니다.");
        }
    }

    @Override
    public void checkNicknameDuplication(String nickname) {
        if (memberRepository.existsMemberByNickname(nickname)) {
            throw new DataDuplicateKeyException("이미 존재하는 닉네임 입니다.");
        }
    }

    @Transactional
    @Override
    public void signUpMember(JoinMemberRequest request) {
        Member member = Member.from(request, cryptPasswordService.encryptPassword(request.getPassword()));
        checkNicknameDuplication(member.getNickname());
        checkUserNameDuplication(member.getUsername());

        try {
            memberRepository.save(member);
        } catch (DataIntegrityViolationException e) {
            throw new DataDuplicateKeyException("이미 등록된 회원 정보입니다.");
        }
    }
}
