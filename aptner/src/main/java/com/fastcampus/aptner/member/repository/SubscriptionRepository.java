package com.fastcampus.aptner.member.repository;

import com.fastcampus.aptner.member.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}
