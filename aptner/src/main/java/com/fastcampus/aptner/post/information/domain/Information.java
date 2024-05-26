package com.fastcampus.aptner.post.information.domain;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "information")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "information_id")
    private Long informationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

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

    @Column(name = "view")
    private Long view;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "information_category_id")
    private InformationCategory informationCategoryId;
    @Builder
    public Information(Member memberId, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt, PostStatus status, Long view, InformationCategory informationCategoryId) {
        this.memberId = memberId;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.status = status;
        this.view = view;
        this.informationCategoryId = informationCategoryId;
    }

    public static Information from(Member member, InformationCategory informationCategory, InformationDTO.InformationPostReqDTO dto){
        return Information.builder()
                .informationCategoryId(informationCategory)
                .memberId(member)
                .title(dto.title())
                .contents(dto.contents())
                .status(dto.status())
                .view(0L)
                .build();
    }
    public void updateInformation(InformationCategory informationCategory, InformationDTO.InformationPostReqDTO dto){
        this.title = dto.title();
        this.contents = dto.contents();
        this.informationCategoryId= informationCategory;
        this.status = dto.status();
    }
    public void deleteInformation(){this.status=PostStatus.DELETED;}
    public void hideInformation(){this.status=PostStatus.HIDDEN;}

    public void addViewCount(){
        this.view++;
    }
}
