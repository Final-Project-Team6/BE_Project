package com.fastcampus.aptner.member.domain;


import com.fastcampus.aptner.global.handler.common.BaseTimeEntity;
import com.fastcampus.aptner.jwt.domain.TokenStorage;
import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.common.domain.Comment;
import com.fastcampus.aptner.post.common.domain.Vote;
import com.fastcampus.aptner.post.communication.domain.Communication;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.information.domain.Information;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.antlr.v4.runtime.Token;
import org.hibernate.validator.constraints.Length;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
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
    @OneToMany(mappedBy = "memberId")
    private List<MemberRole> memberRole = new ArrayList<>(); // 회원 권한

    @JsonIgnore
    @OneToMany(mappedBy = "memberId")
    private List<MemberHome> memberHome = new ArrayList<>();  // 회원-자택

    @JsonIgnore
    @OneToOne(mappedBy = "memberId")
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

    @JsonIgnore
    @OneToOne(mappedBy = "memberId")
    private TokenStorage tokenStorage; // 토큰 저장소
    
}
