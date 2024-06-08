package com.fastcampus.aptner.post.opinion.domain;

import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.global.handler.common.BaseTimeEntity;
import com.fastcampus.aptner.global.handler.exception.CustomAPIException;
import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.common.enumType.BoardType;
import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.communication.domain.Communication;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.opinion.dto.VoteDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.fastcampus.aptner.post.common.error.PostErrorCode.COMMENT_CONVERT_FAILED;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "comment")
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communication_id")
    private Communication communicationId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id")
    private Complaint complaintId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_id")
    private Announcement announcementId;

    @Setter
    @Column(name = "contents", nullable = false)
    private String contents;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", length = 50, nullable = false)
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    @Setter
    @Column(name = "status", nullable = false)
    private PostStatus status;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Comment parentId;

    @OneToMany(mappedBy = "parentId")
    private List<Comment> children = new ArrayList<>();

    @OneToMany(mappedBy = "commentId", fetch = FetchType.LAZY)
    private List<Vote> voteList = new ArrayList<>();

    @Builder
    public Comment(Member memberId, Communication communicationId, Complaint complaintId, Announcement announcementId, String contents, PostStatus status, Comment parentId) {
        this.memberId = memberId;
        this.communicationId = communicationId;
        this.complaintId = complaintId;
        this.announcementId = announcementId;
        this.contents = contents;
        this.status = status;
        this.parentId = parentId;
    }

    public VoteDTO.VoteRespDTO aboutVote(JWTMemberInfoDTO request) {
        int agreeCnt = getAgreeCount();
        int total = voteList.size();
        return new VoteDTO.VoteRespDTO(total, agreeCnt, total - agreeCnt, yourVote(request));
    }

    public int getAgreeCount() {
        int cnt = 0;
        for (Vote v : voteList) {
            if (v.isOpinion()) {
                cnt++;
            }
        }
        return cnt;
    }

    public Boolean yourVote(JWTMemberInfoDTO request) {
        if (request == null) return null;
        for (Vote v : voteList) {
            if (v.getMemberId().getMemberId().equals(request.getMemberId())) {
                return v.isOpinion();
            }
        }
        return null;
    }

    public CommentDTO.Post getPost() {
        try {
            Comment first = getFirst();
            CommentDTO.Post post = null;
            if (first.communicationId != null) {
                post = CommentDTO.Post.builder()
                        .postId(first.communicationId.getCommunicationId())
                        .postTitle(first.communicationId.getTitle())
                        .boardType(BoardType.COMMUNICATION)
                        .build();

            } else if (first.complaintId != null) {
                post = CommentDTO.Post.builder()
                        .postId(first.complaintId.getComplaintId())
                        .postTitle(first.complaintId.getTitle())
                        .boardType(BoardType.COMPLAINT)
                        .build();
            } else if (first.announcementId != null) {
                post = CommentDTO.Post.builder()
                        .postId(first.announcementId.getAnnouncementId())
                        .postTitle(first.announcementId.getTitle())
                        .boardType(BoardType.ANNOUNCEMENT)
                        .build();
            } else {
                throw new RestAPIException(COMMENT_CONVERT_FAILED);
            }
            return post;
        }catch (Exception e){
            throw new CustomAPIException(e.getMessage());
        }
    }

    public Comment getFirst() {
        if (parentId != null) {
            return parentId.getFirst();
        } else {
            return this;
        }
    }

    public int getCommentCnt(){
        int cnt = 0;
        if (children.size()!=0) {
            cnt += children.size();
            for (Comment comment : children) {
                cnt += comment.getCommentCnt();
            }
        }
        return cnt;
    }
}
