package com.fastcampus.aptner.member.domain;

import com.fastcampus.aptner.apartment.domain.Home;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"homeId", "memberId"})
@Table(name = "member_home")
@Entity
public class MemberHome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_home_id")
    private Long memberHomeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_id")
    private Home homeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @Builder
    public MemberHome(Home homeId, Member memberId) {
        this.homeId = homeId;
        this.memberId = memberId;
    }

    public void changeMember(Member member) {
        this.memberId = member;
    }
}
