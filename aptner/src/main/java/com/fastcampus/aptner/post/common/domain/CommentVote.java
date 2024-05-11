package com.fastcampus.aptner.post.common.domain;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.communication.domain.Communication;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment_vote")
public class CommentVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_vote_id")
    private Long commentVoteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment commentId;

    @Column(name = "opinion")
    private boolean opinion;
}
