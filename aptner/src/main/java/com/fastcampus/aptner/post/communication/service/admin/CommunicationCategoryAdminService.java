package com.fastcampus.aptner.post.communication.service.admin;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.communication.dto.CommunicationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface CommunicationCategoryAdminService {

    ResponseEntity<HttpStatus> createCommunicationCategory(JWTMemberInfoDTO userToken , Long apartmentId, CommunicationDTO.CommunicationCategoryReqDTO dto);
    ResponseEntity<HttpStatus> updateCommunicationCategory(JWTMemberInfoDTO userToken , Long communicationCategoryId, CommunicationDTO.CommunicationCategoryReqDTO dto);
    ResponseEntity<HttpStatus> deleteCommunicationCategory(JWTMemberInfoDTO userToken , Long CommunicationCategoryId);
}
