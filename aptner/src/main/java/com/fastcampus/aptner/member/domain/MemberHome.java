package com.fastcampus.aptner.member.domain;

import com.fastcampus.aptner.apartment.domain.Home;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "member_home")
@Entity
public class MemberHome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_home_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_id")
    private Home home;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public MemberHome(Home home, Member member) {
        this.home = home;
        this.member = member;
    }

    public void changeMember(Member member) {
        this.member = member;
    }
}
