package com.fastcampus.aptner.post.complaint.service.admin;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.complaint.domain.ComplaintStatus;
import com.fastcampus.aptner.post.complaint.repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ComplaintAdminServiceImpl implements ComplaintAdminService {

    private final ComplaintRepository complaintRepository;

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateComplaintStatus(JWTMemberInfoDTO token, Long complaintId, ComplaintStatus complaintStatus) {
        //todo 권한 확인
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(NoSuchElementException::new);
        complaint.changeComplaintStatus(complaintStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
