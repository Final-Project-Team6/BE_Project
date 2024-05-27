package com.fastcampus.aptner.member.repository;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByMemberId(Member member);

}
