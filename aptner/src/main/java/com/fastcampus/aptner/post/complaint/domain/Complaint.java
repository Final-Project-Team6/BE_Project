package com.fastcampus.aptner.post.complaint.domain;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.common.PostStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "compaint")
@Entity
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_category_id")
    private ComplaintCategory complaintCategoryId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "contents" , nullable = false)
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

    @Enumerated(EnumType.STRING)
    @Column(name = "complaint_status", nullable = false)
    private ComplaintStatus complaintStatus;

    @Column(name = "secret", nullable = false)
    private boolean secret;

    @JsonIgnore
    @OneToMany(mappedBy = "complaintId")
    private List<ComplaintComment> complaintCommentList = new ArrayList<>();
}
