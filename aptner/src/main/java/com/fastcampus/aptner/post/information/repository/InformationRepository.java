package com.fastcampus.aptner.post.information.repository;

import com.fastcampus.aptner.post.information.domain.Information;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationRepository extends JpaRepository<Information,Long>,InformationRepositoryDsl {
}
