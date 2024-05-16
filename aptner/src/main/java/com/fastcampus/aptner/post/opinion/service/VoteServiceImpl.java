package com.fastcampus.aptner.post.opinion.service;

import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.announcement.service.AnnouncementCommonService;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.complaint.service.ComplaintCommonService;
import com.fastcampus.aptner.post.opinion.domain.Comment;
import com.fastcampus.aptner.post.opinion.domain.Vote;
import com.fastcampus.aptner.post.opinion.domain.VoteType;
import com.fastcampus.aptner.post.opinion.repository.CommentRepository;
import com.fastcampus.aptner.post.opinion.repository.VoteRepository;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
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
import java.util.Optional;

import static com.fastcampus.aptner.post.common.error.VoteErrorCode.ALREADY_EXiSTS;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VoteServiceImpl implements VoteService{

    private final VoteRepository voteRepository;
    private final MemberCommonService memberCommonService;
    private final AnnouncementCommonService announcementCommonService;
    private final CommentCommonService commentCommonService;
    private final ComplaintCommonService complaintCommonService;

    @Override
    public ResponseEntity<HttpStatus> voteToPost(MemberTempDTO.MemberAuthDTO token, Long postId, VoteType voteType, Boolean opinion){
        Member member = memberCommonService.getUserByToken(token);
        Vote vote = null;
        switch (voteType){
            case ANNOUNCEMENT -> {
                Announcement announcement = announcementCommonService.getAnnouncementEntity(postId);
                if (voteRepository.existsByAnnouncementIdAndMemberId(announcement,member)){
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
                if (voteRepository.existsByCommentIdAndMemberId(comment,member)){
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
                if (voteRepository.existsByComplaintIdAndMemberId(complaint,member)){
                    throw new RestAPIException(ALREADY_EXiSTS);
                }
                vote = Vote.builder()
                        .opinion(opinion)
                        .memberId(member)
                        .complaintId(complaint)
                        .build();
            }
            //todo 소통
        }
        if (vote!=null){
            voteRepository.save(vote);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteVote(MemberTempDTO.MemberAuthDTO token, Long postId, VoteType voteType){
        Member member = memberCommonService.getUserByToken(token);
        Vote vote = null;
        switch (voteType){
            case ANNOUNCEMENT ->{
                Announcement announcement = announcementCommonService.getAnnouncementEntity(postId);
                vote = voteRepository.findByAnnouncementIdAndMemberId(announcement,member).orElseThrow(NoSuchElementException::new);
            }
            case COMMENT -> {
                Comment comment = commentCommonService.getCommentEntity(postId);
                vote = voteRepository.findByCommentIdAndMemberId(comment,member).orElseThrow(NoSuchElementException::new);
            }
            //todo 민원, 소통
        }
        if (vote!=null){
            voteRepository.delete(vote);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
