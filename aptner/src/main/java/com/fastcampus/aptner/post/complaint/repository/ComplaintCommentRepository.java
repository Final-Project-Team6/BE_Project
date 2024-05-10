package com.fastcampus.aptner.post.complaint.repository;

import com.fastcampus.aptner.post.complaint.domain.ComplaintComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintCommentRepository extends JpaRepository<ComplaintComment,Long> {
}
