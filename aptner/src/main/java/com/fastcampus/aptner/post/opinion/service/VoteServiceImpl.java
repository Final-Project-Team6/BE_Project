package com.fastcampus.aptner.post.opinion.service;

import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.service.MemberCommonService;
import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.announcement.service.AnnouncementCommonService;
import com.fastcampus.aptner.post.communication.domain.Communication;
import com.fastcampus.aptner.post.communication.service.CommunicationCommonService;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.complaint.service.ComplaintCommonService;
import com.fastcampus.aptner.post.opinion.domain.Comment;
import com.fastcampus.aptner.post.opinion.domain.Vote;
import com.fastcampus.aptner.post.opinion.domain.VoteType;
import com.fastcampus.aptner.post.opinion.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.fastcampus.aptner.global.error.CommonErrorCode.MUST_AUTHORIZE;
import static com.fastcampus.aptner.post.common.error.PostErrorCode.NO_SUCH_POST;
import static com.fastcampus.aptner.post.common.error.VoteErrorCode.ALREADY_EXiSTS;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final MemberCommonService memberCommonService;
    private final AnnouncementCommonService announcementCommonService;
    private final CommentCommonService commentCommonService;
    private final ComplaintCommonService complaintCommonService;
    private final CommunicationCommonService communicationCommonService;

    @Override
    public ResponseEntity<HttpStatus> voteToPost(JWTMemberInfoDTO token, Long postId, VoteType voteType, Boolean opinion) {
        Member member = getMember(token);
        if (opinion == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Vote vote = null;
        switch (voteType) {
            case ANNOUNCEMENT -> {
                Announcement announcement = announcementCommonService.getAnnouncementEntity(postId);
                if (voteRepository.existsByAnnouncementIdAndMemberId(announcement, member)) {
                    throw new RestAPIException(ALREADY_EXiSTS);
                }
                vote = Vote.builder()
                        .opinion(opinion)
                        .memberId(member)
                        .announcementId(announcement)
                        .build();
            }
            case COMMENT -> {
                Comment comment = commentCommonService.getCommentEntity(postId);
                if (voteRepository.existsByCommentIdAndMemberId(comment, member)) {
                    throw new RestAPIException(ALREADY_EXiSTS);
                }
                vote = Vote.builder()
                        .opinion(opinion)
                        .memberId(member)
                        .commentId(comment)
                        .build();
            }
            case COMPLAINT -> {
                Complaint complaint = complaintCommonService.getComplaintEntity(postId);
                if (voteRepository.existsByComplaintIdAndMemberId(complaint, member)) {
                    throw new RestAPIException(ALREADY_EXiSTS);
                }
                vote = Vote.builder()
                        .opinion(opinion)
                        .memberId(member)
                        .complaintId(complaint)
                        .build();
            }
            case COMMUNICATION -> {
                Communication communication = communicationCommonService.getCommunicationEntity(postId);
                if (voteRepository.existsByCommunicationIdAndMemberId(communication, member)) {
                    throw new RestAPIException(ALREADY_EXiSTS);
                }
                vote = Vote.builder()
                        .opinion(opinion)
                        .memberId(member)
                        .communicationId(communication)
                        .build();
            }
        }
        if (vote != null) {
            voteRepository.save(vote);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteVote(JWTMemberInfoDTO token, Long postId, VoteType voteType) {
        Member member = getMember(token);
        Vote vote = getVoteByVoteType(postId, voteType, member);
        if (vote != null) {
            voteRepository.delete(vote);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateVote(JWTMemberInfoDTO token, Long postId, VoteType voteType, Boolean opinion) {
        Member member = getMember(token);
        if (opinion == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Vote vote = getVoteByVoteType(postId, voteType, member);
        if (vote != null) {
            vote.setOpinion(opinion);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private Vote getVoteByVoteType(Long postId, VoteType voteType, Member member) {
        Vote vote = null;
        switch (voteType) {
            case ANNOUNCEMENT -> {
                Announcement announcement = announcementCommonService.getAnnouncementEntity(postId);
                vote = voteRepository.findByAnnouncementIdAndMemberId(announcement, member).orElseThrow(() -> new RestAPIException(NO_SUCH_POST));
            }
            case COMMENT -> {
                Comment comment = commentCommonService.getCommentEntity(postId);
                vote = voteRepository.findByCommentIdAndMemberId(comment, member).orElseThrow(() -> new RestAPIException(NO_SUCH_POST));
            }
            case COMPLAINT -> {
                Complaint complaint = complaintCommonService.getComplaintEntity(postId);
                vote = voteRepository.findByComplaintIdAndMemberId(complaint, member).orElseThrow(() -> new RestAPIException(NO_SUCH_POST));
            }
            case COMMUNICATION -> {
                Communication communication = communicationCommonService.getCommunicationEntity(postId);
                vote = voteRepository.findByCommunicationIdAndMemberId(communication, member).orElseThrow(() -> new RestAPIException(NO_SUCH_POST));
            }
        }
        return vote;
    }

    private Member getMember(JWTMemberInfoDTO token) {
        if (token == null) {
            throw new RestAPIException(MUST_AUTHORIZE);
        }
        return memberCommonService.getUserByToken(token);
    }
}
