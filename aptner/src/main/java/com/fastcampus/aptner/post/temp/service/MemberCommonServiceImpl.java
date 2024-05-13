package com.fastcampus.aptner.post.temp.service;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import com.fastcampus.aptner.post.temp.repository.TempMemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberCommonServiceImpl implements MemberCommonService {

    private final TempMemberRepository memberRepository;

    @Override
    public Member getUserByToken(MemberTempDTO.MemberAuthDTO dto) {
        return memberRepository.findById(1L).orElse(null);
    }
}
