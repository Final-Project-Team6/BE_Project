package com.fastcampus.aptner.post.complaint.service;

import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.common.error.PostErrorCode;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.complaint.domain.ComplaintCategory;
import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import com.fastcampus.aptner.post.complaint.repository.ComplaintRepository;
import com.fastcampus.aptner.post.opinion.domain.CommentType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.opinion.service.CommentCommonService;
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

import java.util.List;
import java.util.NoSuchElementException;

import static com.fastcampus.aptner.post.common.error.PostErrorCode.NOT_SAME_USER;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComplaintServiceImpl implements ComplaintService{

    private final ComplaintRepository complaintRepository;
    private final ComplaintCommonService complaintCommonService;
    private final MemberCommonService memberCommonService;
    private final CommentCommonService commentCommonService;


    @Override
    public ResponseEntity<HttpStatus> uploadComplaint(MemberTempDTO.MemberAuthDTO userToken, Long apartmentId, ComplaintDTO.ComplaintReqDTO dto){
        Member member = memberCommonService.getUserByToken(userToken);
        isCorrectApartment(userToken,apartmentId);
        ComplaintCategory category = complaintCommonService.getComplaintCategoryEntity(dto.complaintCategoryId());
        complaintRepository.save(Complaint.from(dto,member,category));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateComplaint(MemberTempDTO.MemberAuthDTO userToken,Long complaintId, ComplaintDTO.ComplaintReqDTO dto){
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(NoSuchElementException::new);
        isSameUser(complaint.getMemberId().getId(),userToken);
        ComplaintCategory category = complaintCommonService.getComplaintCategoryEntity(dto.complaintCategoryId());
        complaint.updateComplaint(dto,category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteComplaint(MemberTempDTO.MemberAuthDTO userToken,Long complaintId){
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(NoSuchElementException::new);
        isSameUser(complaint.getMemberId().getId(),userToken);
        complaint.deleteComplaint();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> getComplaint(MemberTempDTO.MemberAuthDTO token, Long complaintId){
        Member viewer = memberCommonService.getUserByToken(token);
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(NoSuchElementException::new);
        if (complaint.isSecret()){
            if (viewer.getId()!=token.memberId()){
                //todo 권한에 따른 접근
                throw new RestAPIException(NOT_SAME_USER);
            }
        }
        List<CommentDTO.ViewComments> comments = commentCommonService.getComments(complaintId, CommentType.COMPLAINT,token);
        ComplaintDTO.ComplaintRespDTO resp = new ComplaintDTO.ComplaintRespDTO(complaint,token,comments);
        complaint.addViewCount();
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }


    private static void isCorrectApartment(MemberTempDTO.MemberAuthDTO userToken, Long apartmentId){
        if (userToken.ApartmentId()!= apartmentId){
            throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
        }
    }

    private static void isSameUser(Long memberId,MemberTempDTO.MemberAuthDTO userToken){
        if (userToken.memberId()!= memberId){
            throw new RestAPIException(NOT_SAME_USER);
        }
    }
}
