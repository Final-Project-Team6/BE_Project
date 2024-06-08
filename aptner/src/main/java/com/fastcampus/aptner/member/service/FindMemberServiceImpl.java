package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.global.handler.exception.CustomAPIException;
import com.fastcampus.aptner.global.handler.exception.CustomDataNotFoundException;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.dto.response.FindUsernameResponse;
import com.fastcampus.aptner.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FindMemberServiceImpl implements FindMemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public FindUsernameResponse getMemberByPhoneAndFullName(String phone, String fullName) {

        Member member = memberRepository.findMemberByPhoneAndFullName(phone, fullName)
                .orElseThrow(() -> new CustomDataNotFoundException("휴대전화번호, 이름을 다시 확인해주세요."));

        return FindUsernameResponse.builder()
                .fullName(member.getFullName())
                .username(member.getUsername())
                .build();
    }

    @Transactional
    @Override
    public Long getMemberByUserNameAndFullNameAndPhone(String username, String fullName, String phone) {
        Member member = memberRepository.findMemberByPhoneAndFullNameAndUsername(phone, fullName, username)
                .orElseThrow(() -> new CustomDataNotFoundException("휴대전화번호, 이름, 아이디를 다시 확인해주세요."));

        return member.getMemberId();
    }

    @Transactional
    @Override
    public void modificationPassword(Long id, String password, String memberIdUUID) {
        Member member = memberRepository.findMemberByMemberId(id)
                .orElseThrow(() -> new CustomDataNotFoundException("회원이 존재하지 않습니다."));

        UUID getMemberIdUUID = UUID.nameUUIDFromBytes(member.getMemberId().toString().getBytes(StandardCharsets.UTF_8));

        if (!getMemberIdUUID.toString().equals(memberIdUUID)) {
            throw new CustomAPIException("잘못된 접근 입니다.");
        }

        memberRepository.updatePasswordById(member.getMemberId(), bCryptPasswordEncoder.encode(password));

    }
}
