package com.fastcampus.aptner.post.communication.domain;

import com.fastcampus.aptner.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "communication_vote")
@Entity
public class CommunicationVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "communication_vote_id")
    private Long communicationVoteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communication_id")
    private Communication communicationId;

    @Column(name = "opinion")
    private boolean opinion;


}
