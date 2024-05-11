package com.fastcampus.aptner.post.announcement.domain;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.global.handler.common.BaseTimeEntity;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.common.domain.PostStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "announcement")
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

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", length = 50, nullable = false)
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PostStatus status;

    @Column(name = "view", nullable = false)
    private Long view;

    @Column(name = "important", nullable = false)
    private Integer important;

}
