package com.fastcampus.aptner.post.information.service;

import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface InformationService {

    ResponseEntity<HttpStatus> uploadInformation(MemberTempDTO.MemberAuthDTO userToken, Long apartmentId, InformationDTO.InformationPostReqDTO dto);

    ResponseEntity<HttpStatus> updateInformation(MemberTempDTO.MemberAuthDTO userToken, Long informaationId, InformationDTO.InformationPostReqDTO dto);

    ResponseEntity<HttpStatus> deleteInformation(MemberTempDTO.MemberAuthDTO userToken, Long informationId);
}
