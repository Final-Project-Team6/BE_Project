package com.fastcampus.aptner.post.announcement.repository;

import com.fastcampus.aptner.post.announcement.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long>, AnnouncementRepositoryDsl {

}
