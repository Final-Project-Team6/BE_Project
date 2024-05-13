package com.fastcampus.aptner.post.announcement.service;

import com.fastcampus.aptner.post.announcement.domain.AnnouncementCategory;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AnnouncementCategoryService {

    ResponseEntity<List<AnnouncementDTO.AnnouncementCategoryRespDTO>> getAnnouncementCategoryList(Long apartmentId);


}
