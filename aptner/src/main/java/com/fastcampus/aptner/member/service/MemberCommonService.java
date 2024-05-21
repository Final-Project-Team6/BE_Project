package com.fastcampus.aptner.member.service;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.domain.Member;

public interface MemberCommonService {

    Member getUserByToken(JWTMemberInfoDTO dto);

}
