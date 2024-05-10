package com.fastcampus.aptner.post.complaint.repository;

import com.fastcampus.aptner.post.complaint.domain.ComplaintCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintCategoryRepository extends JpaRepository<ComplaintCategory,Long> {
}
