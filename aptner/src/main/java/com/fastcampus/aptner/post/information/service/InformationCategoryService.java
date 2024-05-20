package com.fastcampus.aptner.post.information.service;

import com.fastcampus.aptner.post.information.dto.InformationDTO;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface InformationCategoryService {

    ResponseEntity<HttpStatus> createInformationCategory(MemberTempDTO.MemberAuthDTO userToken , Long apartmentId, InformationDTO.InformationCategoryReqDTO dto);
    ResponseEntity<HttpStatus> updateInformationCategory(MemberTempDTO.MemberAuthDTO userToken , Long informationCategoryId, InformationDTO.InformationCategoryReqDTO dto);
    ResponseEntity<HttpStatus> deleteInformationCategory(MemberTempDTO.MemberAuthDTO userToken , Long InformationCategoryId);
}
