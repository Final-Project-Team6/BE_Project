package com.fastcampus.aptner.post.complaint.service.admin;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.complaint.domain.ComplaintStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ComplaintAdminService {

    ResponseEntity<HttpStatus> updateComplaintStatus(JWTMemberInfoDTO token, Long complaintId, ComplaintStatus complaintStatus);
}
