package com.fastcampus.aptner.post.communication.repository;

import com.fastcampus.aptner.post.communication.domain.Communication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunicationRepository extends JpaRepository<Communication,Long>,CommunicationRepositoryDsl {
}
