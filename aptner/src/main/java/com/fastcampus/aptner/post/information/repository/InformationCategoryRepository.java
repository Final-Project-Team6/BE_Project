package com.fastcampus.aptner.post.information.repository;

import com.fastcampus.aptner.post.information.domain.InformationCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationCategoryRepository extends JpaRepository<InformationCategory,Long> {
}
