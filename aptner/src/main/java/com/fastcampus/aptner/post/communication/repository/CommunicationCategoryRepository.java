package com.fastcampus.aptner.post.communication.repository;

import com.fastcampus.aptner.post.communication.domain.CommunicationCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunicationCategoryRepository extends JpaRepository<CommunicationCategory,Long> {
}
