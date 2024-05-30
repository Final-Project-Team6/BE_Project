package com.fastcampus.aptner.post.communication.service;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.common.dto.PageResponseDTO;
import com.fastcampus.aptner.post.communication.dto.CommunicationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface CommunicationService {


    ResponseEntity<HttpStatus> uploadCommunication(JWTMemberInfoDTO userToken, Long apartmentId, CommunicationDTO.CommunicationPostReqDTO dto);

    ResponseEntity<HttpStatus> updateCommunication(JWTMemberInfoDTO userToken, Long communicationId, CommunicationDTO.CommunicationPostReqDTO dto);

    ResponseEntity<HttpStatus> deleteCommunication(JWTMemberInfoDTO userToken, Long communicationId);

    ResponseEntity<PageResponseDTO> searchCommunication(CommunicationDTO.CommunicationSearchReqDTO reqDTO,JWTMemberInfoDTO memberToken);

    ResponseEntity<CommunicationDTO.CommunicationRespDTO> getCommunication(Long communicationtId, JWTMemberInfoDTO token);


}
