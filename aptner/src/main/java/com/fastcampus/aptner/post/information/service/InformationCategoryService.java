package com.fastcampus.aptner.post.information.service;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface InformationCategoryService {

    ResponseEntity<HttpStatus> createInformationCategory(JWTMemberInfoDTO userToken , Long apartmentId, InformationDTO.InformationCategoryReqDTO dto);
    ResponseEntity<HttpStatus> updateInformationCategory(JWTMemberInfoDTO userToken , Long informationCategoryId, InformationDTO.InformationCategoryReqDTO dto);
    ResponseEntity<HttpStatus> deleteInformationCategory(JWTMemberInfoDTO userToken , Long InformationCategoryId);
}
