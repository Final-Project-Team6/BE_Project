package com.fastcampus.aptner.post.complaint.service.admin;

import com.fastcampus.aptner.post.complaint.domain.ComplaintStatus;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface ComplaintAdminService {

    ResponseEntity<HttpStatus> updateComplaintStatus(MemberTempDTO.MemberAuthDTO token,Long complaintId,ComplaintStatus complaintStatus);
}
