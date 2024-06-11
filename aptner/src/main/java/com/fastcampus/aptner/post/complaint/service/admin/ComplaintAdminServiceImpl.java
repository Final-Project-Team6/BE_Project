package com.fastcampus.aptner.post.complaint.service.admin;

import com.fastcampus.aptner.global.error.RestAPIException;
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

import static com.fastcampus.aptner.post.common.error.PostErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ComplaintAdminServiceImpl implements ComplaintAdminService {

    private final ComplaintRepository complaintRepository;

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateComplaintStatus(JWTMemberInfoDTO token, Long complaintId, ComplaintStatus complaintStatus) {
        
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(()->new RestAPIException(NO_SUCH_POST));
        if (complaint.getComplaintCategoryId().getApartmentId().getApartmentId() != token.getApartmentId()){
            throw new RestAPIException(NOT_ALLOWED_APARTMENT);
        }
        if (token.getRoleName().equals("MANAGER") || token.getRoleName().equals("ADMIN")){
            complaint.changeComplaintStatus(complaintStatus);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
