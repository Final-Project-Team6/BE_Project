package com.fastcampus.aptner.post.temp.service;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;

public interface MemberCommonService {

    Member getUserByToken(MemberTempDTO.MemberAuthDTO dto);

}
