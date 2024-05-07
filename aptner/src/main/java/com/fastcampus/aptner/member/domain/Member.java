package com.fastcampus.aptner.member.domain;


import com.fastcampus.aptner.global.handler.common.BaseTimeEntity;
import com.fastcampus.aptner.member.dto.response.UpdateMemberDetailsResponse;
import com.fastcampus.aptner.member.service.CryptPasswordService;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "members_tb")
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id; // 회원 고유 번호

    @Column(nullable = false, length = 20)
    private String username; // 회원 아이디

    @Column(nullable = false, length = 120) // 비밀번호 인코딩: BCrypt
    private String password; // 회원 비밀번호

    @Column(nullable = false, length = 15)
    private String nickname; // 회원 닉네임

    @Column(nullable = false, unique = true, length = 20)
    private String fullName; // 회원 이름

    @Column(nullable = false, length = 20)
    private String phone; // 회원 전화번호

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole; // 회원 권한

    @Builder
    public Member(String username, String password, String nickname, String fullName, String phone, MemberRole memberRole) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.fullName = fullName;
        this.phone = phone;
        this.memberRole = memberRole;
    }

    public void changePassword(String enterPassword, CryptPasswordService cryptPasswordService) {
        this.password = cryptPasswordService.encryptPassword(enterPassword);
    }

    public Member updateDetailsMember(UpdateMemberDetailsResponse response) {
        if (response.getNickname() != null) {
            this.nickname = response.getNickname();
        }
        return this;
    }


}
