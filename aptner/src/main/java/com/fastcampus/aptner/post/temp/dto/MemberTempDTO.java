package com.fastcampus.aptner.post.temp.dto;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.domain.RoleName;

public class MemberTempDTO {

    public record MemberAuthDTO(Long memberId, RoleName role, Long ApartmentId){}
    public record MemberRespDTO(Long memberId, String nickname){
        public MemberRespDTO(Member member){
            this(member.getMemberId(),member.getNickname());
        }
    }
}
