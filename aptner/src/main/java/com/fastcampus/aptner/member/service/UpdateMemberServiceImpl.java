package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.global.handler.exception.DataNotFoundException;
import com.fastcampus.aptner.global.handler.exception.NotMatchPasswordException;
import com.fastcampus.aptner.global.handler.exception.SamePasswordException;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.dto.response.DetailsMemberResponse;
import com.fastcampus.aptner.member.dto.request.UpdateMemberDetailsRequest;
import com.fastcampus.aptner.member.dto.request.UpdateMemberPasswordRequest;
import com.fastcampus.aptner.member.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Service
public class UpdateMemberServiceImpl implements UpdateMemberService {

    MemberRepository memberRepository;
    CryptPasswordService cryptPasswordService;

    public Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 조회할 수 없습니다."));
    }


    @Override
    public DetailsMemberResponse findMemberDetails(Long id) {
        return DetailsMemberResponse.from(findMember(id));
    }

    @Override
    public void updateMemberPassword(Long id, UpdateMemberPasswordRequest response) {
        Member member = findMember(id);

        if (!cryptPasswordService.isPasswordMatch(response.getBeforePassword(), member.getPassword())) {
            throw new NotMatchPasswordException("비밀번호가 일치하지 않습니다.");
        }

        if (cryptPasswordService.isPasswordMatch(response.getBeforePassword(), member.getPassword())) {
            throw new SamePasswordException("새로운 비밀번호는 이전 비밀번호와 다르게 입력해야 합니다.");
        }

        member.changePassword(response.getAfterPassword(), cryptPasswordService);
        memberRepository.save(member);
    }

    @Override
    public void updateMemberDetails(Long id, UpdateMemberDetailsRequest request) {
        memberRepository.save(findMember(id).updateDetailsMember(request));
    }

    @Override
    public void deleteMember(Long id, String password) {
        Member member = findMember(id);

        if (!cryptPasswordService.isPasswordMatch(password, member.getPassword())) {
            throw new NotMatchPasswordException("비밀번호가 일치하지 않습니다.");
        }

        memberRepository.deleteById(id);

    }
}

