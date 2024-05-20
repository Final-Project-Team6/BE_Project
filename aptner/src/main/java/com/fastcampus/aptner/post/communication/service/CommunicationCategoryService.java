package com.fastcampus.aptner.post.communication.service;


import com.fastcampus.aptner.post.communication.dto.CommunicationDTO;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface CommunicationCategoryService {

    ResponseEntity<HttpStatus> createCommunicationCategory(MemberTempDTO.MemberAuthDTO userToken , Long apartmentId, CommunicationDTO.CommunicationCategoryReqDTO dto);
    ResponseEntity<HttpStatus> updateCommunicationCategory(MemberTempDTO.MemberAuthDTO userToken , Long communicationCategoryId, CommunicationDTO.CommunicationCategoryReqDTO dto);
    ResponseEntity<HttpStatus> deleteCommunicationCategory(MemberTempDTO.MemberAuthDTO userToken , Long CommunicationCategoryId);

}
