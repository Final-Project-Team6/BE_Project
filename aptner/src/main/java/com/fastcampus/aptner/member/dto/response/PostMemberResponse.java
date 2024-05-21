package com.fastcampus.aptner.member.dto.response;

import com.fastcampus.aptner.member.domain.Member;

public record PostMemberResponse(Long memberId, String nickname) {
    public PostMemberResponse(Member member){
        this(member.getMemberId(),member.getNickname());
    }
}
