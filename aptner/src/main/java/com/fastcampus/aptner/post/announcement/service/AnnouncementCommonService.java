package com.fastcampus.aptner.post.announcement.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementCategory;

import java.util.List;

public interface AnnouncementCommonService {

    AnnouncementCategory getAnnouncementCategory(Long announcementCategoryId);

    List<AnnouncementCategory> getAnnouncementCategoryEntityList(Apartment apartment);

    Announcement getAnnouncementEntity(Long announcementId);

}
