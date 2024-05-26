package com.fastcampus.aptner.post.information.service;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.common.dto.PageResponseDTO;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import org.springframework.http.ResponseEntity;

public interface InformationService {

    ResponseEntity<PageResponseDTO> searchInformation(InformationDTO.InformationSearchReqDTO reqDTO);
    ResponseEntity<InformationDTO.InformationRespDTO> getInformation(Long informationId, JWTMemberInfoDTO token);
}
