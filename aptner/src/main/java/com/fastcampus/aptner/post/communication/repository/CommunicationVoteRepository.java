package com.fastcampus.aptner.post.communication.repository;

import com.fastcampus.aptner.post.communication.domain.CommunicationVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunicationVoteRepository extends JpaRepository<CommunicationVote,Long> {
}
