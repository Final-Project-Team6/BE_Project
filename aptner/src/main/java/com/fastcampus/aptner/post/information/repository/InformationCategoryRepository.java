package com.fastcampus.aptner.post.information.repository;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.post.information.domain.InformationCategory;
import com.fastcampus.aptner.post.information.domain.InformationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InformationCategoryRepository extends JpaRepository<InformationCategory,Long> {
    List<InformationCategory> findAllByApartmentId(Apartment apartment);
    List<InformationCategory> findAllByApartmentIdAndType(Apartment apartment, InformationType informationType);
}
