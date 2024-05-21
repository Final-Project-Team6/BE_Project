package com.fastcampus.aptner.post.communication.domain;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.communication.dto.CommunicationDTO;
import com.fastcampus.aptner.post.opinion.domain.Comment;
import com.fastcampus.aptner.post.opinion.domain.Vote;
import com.fastcampus.aptner.post.opinion.dto.VoteDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "communication")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Communication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "communication_id")
    private Long communicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="communication_category_id" )
    private CommunicationCategory communicationCategoryId;

    @Column(name = "title", nullable = false)
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

    @Column
    private Long view;

    @OneToMany(mappedBy = "communicationId", fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "communicationId", fetch = FetchType.LAZY)
    private List<Vote> voteList = new ArrayList<>();
    @Builder
    public Communication(Member memberId, CommunicationCategory communicationCategoryId, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt, PostStatus status, Long view) {
        this.memberId = memberId;
        this.communicationCategoryId = communicationCategoryId;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.status = status;
        this.view = view;
    }

    public static Communication from(Member member, CommunicationCategory communicationCategory, CommunicationDTO.CommunicationPostReqDTO dto){
        return Communication.builder()
                .communicationCategoryId(communicationCategory)
                .memberId(member)
                .title(dto.title())
                .contents(dto.contents())
                .status(dto.status())
                .view(0L)
                .build();
    }

    public void updateCommunication(CommunicationCategory communicationCategory, CommunicationDTO.CommunicationPostReqDTO dto){
        this.title = dto.title();
        this.contents = dto.contents();
        this.communicationCategoryId= communicationCategory;
        this.status = dto.status();
    }

    public void deleteCommunication(){this.status=PostStatus.DELETED;}
    public void hideCommunication(){this.status=PostStatus.HIDDEN;}

    public VoteDTO.VoteRespDTO aboutVote(JWTMemberInfoDTO token) {
        int agreeCnt = getAgreeCount();
        int total = voteList.size();
        return new VoteDTO.VoteRespDTO(total,agreeCnt,total-agreeCnt,null);
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

    public Boolean yourVote(JWTMemberInfoDTO token){
        if (token==null) return null;
        for(Vote v : voteList){
            if (Objects.equals(v.getMemberId().getMemberId(), token.getMemberId())){
                return v.isOpinion();
            }
        }
        return null;
    }

    public void addViewCount(){
        this.view++;
    }
}
