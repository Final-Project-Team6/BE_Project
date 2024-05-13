package com.fastcampus.aptner.post.temp.repository;

import com.fastcampus.aptner.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempMemberRepository extends JpaRepository<Member,Long> {
}
