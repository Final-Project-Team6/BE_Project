package com.fastcampus.aptner.post.announcement.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementCategory;
import com.fastcampus.aptner.post.announcement.repository.AnnouncementCategoryRepository;
import com.fastcampus.aptner.post.announcement.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnnouncementCommonServiceImpl implements AnnouncementCommonService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementCategoryRepository announcementCategoryRepository;


    @Override
    public AnnouncementCategory getAnnouncementCategory(Long announcementCategoryId) {
        return announcementCategoryRepository.findById(announcementCategoryId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<AnnouncementCategory> getAnnouncementCategoryEntityList(Apartment apartment) {
        return announcementCategoryRepository.findAllByApartmentId(apartment);
    }

    @Override
    public Announcement getAnnouncementEntity(Long announcementId) {
        return announcementRepository.findById(announcementId).orElseThrow(NoSuchElementException::new);
    }
}
