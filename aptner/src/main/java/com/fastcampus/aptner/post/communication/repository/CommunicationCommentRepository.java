package com.fastcampus.aptner.post.communication.repository;

import com.fastcampus.aptner.post.communication.domain.CommunicationComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunicationCommentRepository extends JpaRepository<CommunicationComment,Long> {
}
