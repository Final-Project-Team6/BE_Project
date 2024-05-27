package com.fastcampus.aptner.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.fastcampus.aptner.apartment.domain.QApartment.apartment;
import static com.fastcampus.aptner.apartment.domain.QHome.home;
import static com.fastcampus.aptner.member.domain.QMemberHome.memberHome;

@RequiredArgsConstructor
@Repository
public class MemberHomeRepositoryImpl implements MemberHomeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean existsByMemberIdAndApartmentId(Long memberId, Long apartmentId) {
        Integer count = jpaQueryFactory.selectOne()
                .from(memberHome)
                .join(memberHome.homeId, home)
                .join(home.apartmentId, apartment)
                .where(memberHome.memberId.memberId.eq(memberId)
                        .and(apartment.apartmentId.eq(apartmentId)))
                .fetchFirst();

        return count != null;
    }
}