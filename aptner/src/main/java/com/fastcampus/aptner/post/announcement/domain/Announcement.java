package com.fastcampus.aptner.post.announcement.domain;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.global.handler.common.BaseTimeEntity;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fastcampus.aptner.post.opinion.domain.Comment;
import com.fastcampus.aptner.post.opinion.domain.Vote;
import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.opinion.dto.VoteDTO;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "announcement")
@Getter
public class Announcement extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private Long announcementId;

    @ManyToOne
    @JoinColumn(name = "announcement_category_id")
    private AnnouncementCategory announcementCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @Column(name = "title", length = 255 , nullable = false)
    private String title;

    @Column(name = "contents", columnDefinition = "TEXT", nullable = false)
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

    @Column(name = "important", nullable = false)
    private Integer important;

    @OneToMany(mappedBy = "announcementId", fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "announcementId", fetch = FetchType.LAZY)
    private List<Vote> voteList = new ArrayList<>();
    @Builder
    public Announcement(AnnouncementCategory announcementCategoryId, Member memberId, String title, String contents, PostStatus status, Long view, Integer important) {
        this.announcementCategoryId = announcementCategoryId;
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
        this.status = status;
        this.view = view;
        this.important = important;
    }


    public static Announcement from(Member member,AnnouncementCategory announcementCategory, AnnouncementDTO.AnnouncementPostReqDTO dto){
        return Announcement.builder()
                .announcementCategoryId(announcementCategory)
                .memberId(member)
                .title(dto.title())
                .contents(dto.contents())
                .status(dto.status())
                .view(0L)
                .important(dto.important())
                .build();
    }

    public void updateAnnouncement(AnnouncementCategory announcementCategory,AnnouncementDTO.AnnouncementPostReqDTO dto){
        this.title = dto.title();
        this.contents = dto.contents();
        this.important = dto.important();
        this.announcementCategoryId = announcementCategory;
        this.status = dto.status();
    }

    public void deleteAnnouncement(){
        this.status = PostStatus.DELETED;
    }
    public void hideAnnouncement(){this.status = PostStatus.HIDDEN;}

    public VoteDTO.VoteRespDTO aboutVote(MemberTempDTO.MemberAuthDTO token){
        int agreeCnt = getAgreeCount();
        int total = voteList.size();
        return new VoteDTO.VoteRespDTO(total,agreeCnt,total-agreeCnt,yourVote(token));
    }
    public VoteDTO.VoteRespDTO aboutVoteWithoutMember(){
        int agreeCnt = getAgreeCount();
        int total = voteList.size();
        return new VoteDTO.VoteRespDTO(total,agreeCnt,total-agreeCnt,null);
    }

    public int getAgreeCount(){
        int cnt =0;
        for(Vote v : voteList){
            if (v.isOpinion()){
                cnt++;
            }
        }
        return cnt;
    }

    public Boolean yourVote(MemberTempDTO.MemberAuthDTO token){
        if (token==null) return null;
        for(Vote v : voteList){
            if (Objects.equals(v.getMemberId().getId(), token.memberId())){
                return v.isOpinion();
            }
        }
        return null;
    }

    public void addViewCount(){
        this.view++;
    }
}
