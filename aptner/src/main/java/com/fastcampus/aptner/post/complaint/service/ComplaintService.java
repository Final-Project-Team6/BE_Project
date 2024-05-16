package com.fastcampus.aptner.post.complaint.service;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.common.dto.PageResponseDTO;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.complaint.domain.ComplaintCategory;
import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

public interface ComplaintService {
    ResponseEntity<HttpStatus> uploadComplaint(MemberTempDTO.MemberAuthDTO userToken, Long apartmentId, ComplaintDTO.ComplaintReqDTO dto);
    ResponseEntity<HttpStatus> updateComplaint(MemberTempDTO.MemberAuthDTO userToken,Long complaintId, ComplaintDTO.ComplaintReqDTO dto);
    ResponseEntity<HttpStatus> deleteComplaint(MemberTempDTO.MemberAuthDTO userToken,Long complaintId);
    ResponseEntity<?> getComplaint(MemberTempDTO.MemberAuthDTO token, Long complaintId);

    ResponseEntity<PageResponseDTO> searchComplaint(ComplaintDTO.ComplaintSearchReqDTO reqDTO, MemberTempDTO.MemberAuthDTO memberToken);


}
