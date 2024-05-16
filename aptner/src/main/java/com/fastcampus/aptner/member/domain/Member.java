package com.fastcampus.aptner.member.domain;


import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.global.handler.common.BaseTimeEntity;
import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.communication.domain.Communication;
import com.fastcampus.aptner.post.communication.domain.CommunicationComment;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.information.domain.Information;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.fastcampus.aptner.post.opinion.domain.*;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"memberRole",
        "memberHomes",
        "subscription",
        "announcement",
        "complaint",
        "Comment",
        "communication",
        "communicationComment",
        "Vote",
        "information"})
@Table(name = "member")
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false, updatable = false)
    private Long id; // 회원 고유 번호

    @Column(nullable = false, length = 20)
    private String username; // 회원 아이디

    @Column(nullable = false, length = 255) // 비밀번호 인코딩: BCrypt
    private String password; // 회원 비밀번호

    @Column(nullable = false, length = 13)
    private String phone; // 회원 휴대전화번호

    @Column(nullable = false, length = 20)
    private String phoneCarrier; // 회원 통신사

    @Column(nullable = false, length = 6)
    private String birthFirst; // 회원 주민등록번호 앞자리

    @Column(nullable = false, length = 16)
    private String nickname; // 회원 닉네임

    @Column(nullable = false, unique = true, length = 16)
    private String fullName; // 회원 이름

    @Column(nullable = false, updatable = false, length = 1)
    private String gender; // 회원 성별(M, W)

    @Column(nullable = false)
    private LocalDateTime authenticatedAt; // 회원 인증일자

    @Column(nullable = false)
    private boolean authenticationStatus; // 회원 인증여부

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatus status; // 회원 상태여부

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL})
    private List<MemberRole> memberRole = new ArrayList<>(); // 회원 권한

    // TODO : CascadeType.ALL 삭제 정책에 따라 변경이 될 가능성이 크다.
    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL})
    private List<MemberHome> memberHomes = new ArrayList<>();  // 회원-자택

    @JsonIgnore
    @JoinColumn(name = "subscription_id")
    @OneToOne(mappedBy = "member", cascade = {CascadeType.ALL})
    private Subscription subscription; // 동의 여부

    @JsonIgnore
    @OneToMany(mappedBy = "memberId")
    private List<Announcement> announcement = new ArrayList<>(); // 공지사항 게시판

    @JsonIgnore
    @OneToMany(mappedBy = "memberId")
    private List<Complaint> complaint = new ArrayList<>(); // 민원 게시판

    @JsonIgnore
    @OneToMany(mappedBy = "memberId")
    private List<Comment> Comment = new ArrayList<>(); // TODO: 댓글 매핑하기.

    @JsonIgnore
    @OneToMany(mappedBy = "memberId")
    private List<Communication> communication = new ArrayList<>(); // 소통 게시판

    @JsonIgnore
    @OneToMany(mappedBy = "memberId")
    private List<Vote> Vote = new ArrayList<>(); // TODO: 투표 매핑하기.

    @JsonIgnore
    @OneToMany(mappedBy = "memberId")
    private List<Information> information = new ArrayList<>(); // 정보 게시판

    @Builder
    public Member(String username,
                  String password,
                  String phone,
                  String phoneCarrier,
                  String birthFirst,
                  String nickname,
                  String fullName,
                  String gender) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.phoneCarrier = phoneCarrier;
        this.birthFirst = birthFirst;
        this.nickname = nickname;
        this.fullName = fullName;
        this.gender = gender;
        this.authenticatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS); // null 대신 생성일자와 동일한 시간 추가하기.
        this.authenticationStatus = false; // 모든 가입 회원은 최초 1회 인증이 반드시 필요하다.
        this.status = MemberStatus.ACTIVATE; // 인증이 안된 상태이더라도 활성화하기.
    }

    // [연관 관계 메서드]: Member, MemberHome
    public void addMemberHome(MemberHome memberHome) {
        this.memberHomes.add(memberHome);
        memberHome.changeMember(this);
    }

    // [연관 관계 메서드]: Member, MemberHome
    public void addMemberSubscription(Subscription subscription) {
        this.subscriptionId = subscription;
        subscription.changeMember(this);
    }

    // [연관 관계 메서드]: Member, MemberRole
    public void addMemberRole(MemberRole memberRole, Apartment apartment) {
        memberRole.changeApartment(apartment);
        this.memberRole.add(memberRole);
        memberRole.changeMember(this);
    }


}
