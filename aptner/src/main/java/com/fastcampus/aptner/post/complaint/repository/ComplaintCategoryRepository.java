package com.fastcampus.aptner.post.complaint.repository;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.post.complaint.domain.ComplaintCategory;
import com.fastcampus.aptner.post.complaint.domain.ComplaintType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintCategoryRepository extends JpaRepository<ComplaintCategory, Long> {

    List<ComplaintCategory> findAllByApartmentId(Apartment apartment);
    List<ComplaintCategory> findAllByApartmentIdAndType(Apartment apartment, ComplaintType complaintType);
}
