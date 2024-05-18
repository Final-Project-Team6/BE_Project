package com.fastcampus.aptner.member.domain;

import com.fastcampus.aptner.apartment.domain.Apartment;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "member_role")
@Entity
public class MemberRole {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_role_id", nullable = false, updatable = false)
    private Long memberRoleId;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    private Apartment apartmentId;

    @Builder
    public MemberRole(RoleName roleName, Apartment apartmentId) {
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
