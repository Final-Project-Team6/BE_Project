package com.fastcampus.aptner.post.complaint.service;

import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.service.MemberCommonService;
import com.fastcampus.aptner.post.common.dto.PageResponseDTO;
import com.fastcampus.aptner.post.common.error.PostErrorCode;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.complaint.domain.ComplaintCategory;
import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import com.fastcampus.aptner.post.complaint.repository.ComplaintRepository;
import com.fastcampus.aptner.post.opinion.domain.CommentType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.opinion.service.CommentCommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.fastcampus.aptner.global.error.CommonErrorCode.MUST_AUTHORIZE;
import static com.fastcampus.aptner.post.common.error.PostErrorCode.NOT_SAME_USER;
import static com.fastcampus.aptner.post.common.error.PostErrorCode.NO_SUCH_POST;

@Service
@Slf4j
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintCommonService complaintCommonService;
    private final MemberCommonService memberCommonService;
    private final CommentCommonService commentCommonService;


    @Override
    public ResponseEntity<HttpStatus> uploadComplaint(JWTMemberInfoDTO userToken, Long apartmentId, ComplaintDTO.ComplaintReqDTO dto) {
        Member member = memberCommonService.getUserByToken(userToken);
        isCorrectApartment(userToken, apartmentId);
        ComplaintCategory category = complaintCommonService.getComplaintCategoryEntity(dto.complaintCategoryId());
        complaintRepository.save(Complaint.from(dto, member, category));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateComplaint(JWTMemberInfoDTO userToken, Long complaintId, ComplaintDTO.ComplaintReqDTO dto) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(()->new RestAPIException(NO_SUCH_POST));
        isSameUser(complaint.getMemberId().getMemberId(), userToken);
        ComplaintCategory category = complaintCommonService.getComplaintCategoryEntity(dto.complaintCategoryId());
        complaint.updateComplaint(dto, category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteComplaint(JWTMemberInfoDTO userToken, Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(()->new RestAPIException(NO_SUCH_POST));
        isSameUser(complaint.getMemberId().getMemberId(), userToken);
        complaint.deleteComplaint();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> getComplaint(JWTMemberInfoDTO request, Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(()->new RestAPIException(NO_SUCH_POST));
        if (complaint.isSecret()) {
            requestHasRole(request, complaint);
        }
        List<CommentDTO.ViewComments> comments = commentCommonService.getComments(complaintId, CommentType.COMPLAINT, request);
        ComplaintDTO.ComplaintRespDTO resp = new ComplaintDTO.ComplaintRespDTO(complaint, request, comments);
        complaint.addViewCount();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    public ResponseEntity<PageResponseDTO> searchComplaint(ComplaintDTO.ComplaintSearchReqDTO reqDTO, JWTMemberInfoDTO memberToken) {
        PageRequest pageable = PageRequest.of(reqDTO.getPageNumber() - 1, reqDTO.getPageSize());
        reqDTO.setPageable(pageable);
        Page<Complaint> result = complaintRepository.searchComplaint(reqDTO, memberToken);
        if (result.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        PageResponseDTO resp = new PageResponseDTO(result);
        resp.setContent(
                result
                        .getContent()
                        .stream()
                        .map(ComplaintDTO.ComplaintListRespDTO::new)
                        .toList()
        );
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    private static void isCorrectApartment(JWTMemberInfoDTO userToken, Long apartmentId) {
        if (userToken.getApartmentId() != apartmentId) {
            throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
        }
    }

    private static void isSameUser(Long memberId, JWTMemberInfoDTO userToken) {
        if (userToken.getMemberId() != memberId) {
            throw new RestAPIException(NOT_SAME_USER);
        }
    }

    private static void requestHasRole(JWTMemberInfoDTO userToken, Complaint complaint) {
        if (userToken == null) {
            throw new RestAPIException(MUST_AUTHORIZE);
        }
        if (userToken.getMemberId() != complaint.getMemberId().getMemberId()) {
            System.out.println(userToken.getRoleName());
            if (userToken.getRoleName().equals("USER")) {
                throw new RestAPIException(NOT_SAME_USER);
            }
            if (userToken.getApartmentId() != complaint.getComplaintCategoryId().getApartmentId().getApartmentId()) {
                throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
            }
        }
    }
}
