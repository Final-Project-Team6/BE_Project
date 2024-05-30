package com.fastcampus.aptner.post.opinion.service;

import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.service.MemberCommonService;
import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.announcement.service.AnnouncementCommonService;
import com.fastcampus.aptner.post.common.dto.PageResponseDTO;
import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.communication.domain.Communication;
import com.fastcampus.aptner.post.communication.service.CommunicationCommonService;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.complaint.service.ComplaintCommonService;
import com.fastcampus.aptner.post.opinion.domain.Comment;
import com.fastcampus.aptner.post.opinion.domain.CommentType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.opinion.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.fastcampus.aptner.global.error.CommonErrorCode.MUST_AUTHORIZE;
import static com.fastcampus.aptner.post.common.error.PostErrorCode.NOT_SAME_USER;
import static com.fastcampus.aptner.post.common.error.PostErrorCode.NO_SUCH_POST;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final CommentCommonService commentCommonService;
    private final MemberCommonService memberCommonService;
    private final AnnouncementCommonService announcementCommonService;
    private final ComplaintCommonService complaintCommonService;
    private final CommunicationCommonService communicationCommonService;


    @Override
    public ResponseEntity<List<CommentDTO.ViewComments>> getCommentsResp(Long postId, CommentType commentType, JWTMemberInfoDTO request) {
        List<CommentDTO.ViewComments> list = commentCommonService.getComments(postId, commentType, request);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getMyCommentList(JWTMemberInfoDTO request, Integer pageNumber, Integer pageSize) {
        if (request == null) throw new RestAPIException(MUST_AUTHORIZE);
        PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Comment> commentList = commentRepository.getMyComments(request.getMemberId(), pageable);
        if (commentList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        PageResponseDTO resp = new PageResponseDTO(commentList);
        resp.setContent(
                commentList
                        .getContent()
                        .stream()
                        .map(CommentDTO.MyCommentResp::new)
                        .toList()
        );
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> uploadComment(JWTMemberInfoDTO token, Long postId, CommentDTO.UploadCommentReqDTO dto) {
        Member member = getMember(token);
        Comment comment = Comment.builder()
                .memberId(member)
                .contents(dto.contents())
                .status(PostStatus.PUBLISHED)
                .build();
        switch (dto.commentType()) {
            case ANNOUNCEMENT -> {
                Announcement announcement = announcementCommonService.getAnnouncementEntity(postId);
                comment.setAnnouncementId(announcement);
                commentRepository.save(comment);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            case REPLY -> {
                Comment parent = commentCommonService.getCommentEntity(postId);
                comment.setParentId(parent);
                commentRepository.save(comment);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            case COMPLAINT -> {
                Complaint complaint = complaintCommonService.getComplaintEntity(postId);
                comment.setComplaintId(complaint);
                commentRepository.save(comment);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            case COMMUNICATION -> {
                Communication communication = communicationCommonService.getCommunicationEntity(postId);
                comment.setCommunicationId(communication);
                commentRepository.save(comment);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateComment(JWTMemberInfoDTO token, Long commentId, String contents) {
        Member member = getMember(token);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RestAPIException(NO_SUCH_POST));
        if (member.getMemberId() != comment.getMemberId().getMemberId()) {
            throw new RestAPIException(NOT_SAME_USER);
        }
        comment.setContents(contents);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteComment(JWTMemberInfoDTO token, Long commentId) {
        Member member = getMember(token);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RestAPIException(NO_SUCH_POST));
        if (member.getMemberId() != comment.getMemberId().getMemberId()) {
            throw new RestAPIException(NOT_SAME_USER);
        }
        comment.setStatus(PostStatus.DELETED);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Member getMember(JWTMemberInfoDTO token) {
        if (token == null) {
            throw new RestAPIException(MUST_AUTHORIZE);
        }
        return memberCommonService.getUserByToken(token);
    }
}
