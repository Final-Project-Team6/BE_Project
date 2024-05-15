package com.fastcampus.aptner.post.complaint.service.admin;

import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ComplaintCategoryAdminService {
    ResponseEntity<HttpStatus> createComplaintCategory(MemberTempDTO.MemberAuthDTO token, Long apartmentId, ComplaintDTO.ComplaintCategoryReqDTO dto);
    ResponseEntity<HttpStatus> updateComplaintCategory(MemberTempDTO.MemberAuthDTO token, Long complaintCategoryId ,ComplaintDTO.ComplaintCategoryReqDTO dto);
    ResponseEntity<HttpStatus> deleteComplaintCategory(MemberTempDTO.MemberAuthDTO token, Long complaintCategoryId);
}
