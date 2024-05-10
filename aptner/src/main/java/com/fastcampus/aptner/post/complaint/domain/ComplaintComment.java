package com.fastcampus.aptner.post.complaint.domain;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.common.PostStatus;
import com.fastcampus.aptner.post.communication.domain.CommunicationComment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "complaint_comment")
@Entity
public class ComplaintComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id")
    private Complaint complaintId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ComplaintComment parentId;

    @Column(name = "contents")
    private String contents;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", length = 50, nullable = false)
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PostStatus status;

//    @OneToMany(mappedBy = "parentId")
//    private List<ComplaintComment> children = new ArrayList<>();
}
