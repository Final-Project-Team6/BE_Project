package com.fastcampus.aptner.member.domain;

import com.fastcampus.aptner.apartment.domain.Apartment;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "memberRole")
@Entity
public class MemberRole {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberRole_id", nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    @Builder
    public MemberRole(RoleName roleName, Apartment apartment) {
        this.roleName = roleName;
        this.apartmentId = apartmentId;
    }

    public void changeMember(Member memberId) {
        this.memberId = memberId;
    }

    public void changeApartment(Apartment apartmentId){
        this.apartmentId = apartmentId;
    }
}
