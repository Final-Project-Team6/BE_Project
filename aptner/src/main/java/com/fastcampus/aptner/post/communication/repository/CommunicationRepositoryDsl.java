package com.fastcampus.aptner.post.communication.repository;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.communication.domain.Communication;
import com.fastcampus.aptner.post.communication.dto.CommunicationDTO;
import org.springframework.data.domain.Page;

public interface CommunicationRepositoryDsl {
    Page<Communication> searchCommunication(CommunicationDTO.CommunicationSearchReqDTO reqDTO, JWTMemberInfoDTO memberToken);
}
