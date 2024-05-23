package com.fastcampus.aptner.post.complaint.service.admin;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ComplaintCategoryAdminService {
    ResponseEntity<HttpStatus> createComplaintCategory(JWTMemberInfoDTO token, Long apartmentId, ComplaintDTO.ComplaintCategoryReqDTO dto);

    ResponseEntity<HttpStatus> updateComplaintCategory(JWTMemberInfoDTO token, Long complaintCategoryId, ComplaintDTO.ComplaintCategoryReqDTO dto);

    ResponseEntity<HttpStatus> deleteComplaintCategory(JWTMemberInfoDTO token, Long complaintCategoryId);
}
