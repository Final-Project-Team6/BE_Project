package com.fastcampus.aptner.post.announcement.repository;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementCategoryRepository extends JpaRepository<AnnouncementCategory,Long> {

    List<AnnouncementCategory> findAllByApartmentId(Apartment apartment);
}
