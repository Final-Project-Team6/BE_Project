package com.fastcampus.aptner.post.communication.service;

import com.fastcampus.aptner.post.common.dto.PageResponseDTO;
import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.communication.dto.CommunicationDTO;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface CommunicationService {


    ResponseEntity<HttpStatus> uploadCommunication(MemberTempDTO.MemberAuthDTO userToken, Long apartmentId, CommunicationDTO.CommunicationPostReqDTO dto);

    ResponseEntity<HttpStatus> updateCommunication(MemberTempDTO.MemberAuthDTO userToken, Long communicationId, CommunicationDTO.CommunicationPostReqDTO dto);

    ResponseEntity<HttpStatus> deleteCommunication(MemberTempDTO.MemberAuthDTO userToken, Long communicationId);

    ResponseEntity<PageResponseDTO> searchCommunication(CommunicationDTO.CommunicationSearchReqDTO reqDTO);

    ResponseEntity<CommunicationDTO.CommunicationRespDTO> getCommunication(Long communicationtId, MemberTempDTO.MemberAuthDTO token);


}
