package com.fastcampus.aptner.post.complaint.service.admin;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.common.error.PostErrorCode;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.complaint.domain.ComplaintCategory;
import com.fastcampus.aptner.post.complaint.domain.ComplaintStatus;
import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import com.fastcampus.aptner.post.complaint.repository.ComplaintCategoryRepository;
import com.fastcampus.aptner.post.complaint.repository.ComplaintRepository;
import com.fastcampus.aptner.post.complaint.service.ComplaintCommonService;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import com.fastcampus.aptner.post.temp.service.ApartmentCommonService;
import com.fastcampus.aptner.post.temp.service.MemberCommonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
    public ResponseEntity<HttpStatus> updateComplaintStatus(MemberTempDTO.MemberAuthDTO token, Long complaintId, ComplaintStatus complaintStatus) {
        //todo 권한 확인
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(NoSuchElementException::new);
        complaint.changeComplaintStatus(complaintStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
