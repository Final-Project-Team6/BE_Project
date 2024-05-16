package com.fastcampus.aptner.member.repository;

import com.fastcampus.aptner.member.domain.MemberRole;
import com.fastcampus.aptner.member.domain.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {

    // 특정 회원이 특정 아파트에서 어떤 권한을 가지고 있는지 찾는 메서드
    @Query("SELECT mr.roleName FROM MemberRole mr WHERE mr.memberId.memberId = :memberId AND mr.apartmentId.apartmentId = :apartmentId")
    Optional<RoleName> findRoleByMemberAndApartment(@Param("memberId") Long memberId, @Param("apartmentId") Long apartmentId);
}
