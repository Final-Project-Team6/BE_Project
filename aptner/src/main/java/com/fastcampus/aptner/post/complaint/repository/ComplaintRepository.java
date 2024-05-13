package com.fastcampus.aptner.post.complaint.repository;

import com.fastcampus.aptner.post.complaint.domain.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint,Long> {
}
