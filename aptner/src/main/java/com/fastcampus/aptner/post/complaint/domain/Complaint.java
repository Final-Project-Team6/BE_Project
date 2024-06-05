package com.fastcampus.aptner.post.complaint.domain;

import com.fastcampus.aptner.global.handler.common.BaseTimeEntity;
import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import com.fastcampus.aptner.post.opinion.domain.Comment;
import com.fastcampus.aptner.post.opinion.domain.Vote;
import com.fastcampus.aptner.post.opinion.dto.VoteDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "compaint")
@Entity
public class Complaint extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_id")
    private Long complaintId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_category_id")
    private ComplaintCategory complaintCategoryId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "contents", nullable = false)
    private String contents;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", length = 50, nullable = false)
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PostStatus status;

    @Column(name = "view", nullable = false)
    private Long view;

    @Enumerated(EnumType.STRING)
    @Column(name = "complaint_status", nullable = false)
    private ComplaintStatus complaintStatus;

    @Column(name = "secret", nullable = false)
    private boolean secret;

    @JsonIgnore
    @OneToMany(mappedBy = "complaintId")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "complaintId", fetch = FetchType.LAZY)
    private List<Vote> voteList = new ArrayList<>();

    @Builder
    public Complaint(Member memberId, ComplaintCategory complaintCategoryId, String title, String contents, PostStatus status, Long view, ComplaintStatus complaintStatus, boolean secret) {
        this.memberId = memberId;
        this.complaintCategoryId = complaintCategoryId;
        this.title = title;
        this.contents = contents;
        this.status = status;
        this.view = view;
        this.complaintStatus = complaintStatus;
        this.secret = secret;
    }

    public static Complaint from(ComplaintDTO.ComplaintReqDTO dto, Member member, ComplaintCategory complaintCategory) {
        return Complaint.builder()
                .memberId(member)
                .complaintCategoryId(complaintCategory)
                .title(dto.title())
                .contents(dto.contents())
                .status(PostStatus.PUBLISHED)
                .view(0L)
                .complaintStatus(ComplaintStatus.SUBMITTED)
                .secret(dto.secret()).build();
    }

    public void updateComplaint(ComplaintDTO.ComplaintReqDTO dto, ComplaintCategory category) {
        this.complaintCategoryId = category;
        this.title = dto.title();
        this.contents = dto.contents();
        this.secret = dto.secret();
    }

    public void deleteComplaint() {
        this.status = PostStatus.DELETED;
    }

    public void changeComplaintStatus(ComplaintStatus complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public void forceDelete() {
        this.status = PostStatus.FORCE_DELETED;
    }

    public VoteDTO.VoteRespDTO aboutVote(JWTMemberInfoDTO request) {
        int agreeCnt = getAgreeCount();
        int total = voteList.size();
        return new VoteDTO.VoteRespDTO(total, agreeCnt, total - agreeCnt, yourVote(request));
    }

    public VoteDTO.VoteRespDTO aboutVoteWithoutMember() {
        int agreeCnt = getAgreeCount();
        int total = voteList.size();
        return new VoteDTO.VoteRespDTO(total, agreeCnt, total - agreeCnt, null);
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
            if (Objects.equals(v.getMemberId().getMemberId(), request.getMemberId())) {
                return v.isOpinion();
            }
        }
        return null;
    }

    public void addViewCount() {
        this.view++;
    }

    public int getAllCommentCnt(){
        int cnt = 0;
        if (commentList.size()!=0) {
            cnt += commentList.size();
            for (Comment comment : commentList) {
                cnt += comment.getCommentCnt();
            }
        }
        return cnt;
    }
}
