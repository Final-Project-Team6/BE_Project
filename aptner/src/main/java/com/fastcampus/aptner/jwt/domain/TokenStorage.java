package com.fastcampus.aptner.jwt.domain;

import com.fastcampus.aptner.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "tokenStorage")
@Entity
public class TokenStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tokenStorage_id", nullable = false, updatable = false)
    private Long tokenStorageId;

    private String refreshToken;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

}
