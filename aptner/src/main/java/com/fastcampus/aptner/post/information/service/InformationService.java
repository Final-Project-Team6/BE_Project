package com.fastcampus.aptner.post.information.service;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface InformationService {

    ResponseEntity<HttpStatus> uploadInformation(JWTMemberInfoDTO userToken, Long apartmentId, InformationDTO.InformationPostReqDTO dto);

    ResponseEntity<HttpStatus> updateInformation(JWTMemberInfoDTO userToken, Long informaationId, InformationDTO.InformationPostReqDTO dto);

    ResponseEntity<HttpStatus> deleteInformation(JWTMemberInfoDTO userToken, Long informationId);
}
