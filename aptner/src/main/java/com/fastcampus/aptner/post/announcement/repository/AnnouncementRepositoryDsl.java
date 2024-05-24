package com.fastcampus.aptner.post.announcement.repository;

import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import org.springframework.data.domain.Page;

public interface AnnouncementRepositoryDsl {
    Page<Announcement> searchAnnouncement(AnnouncementDTO.AnnouncementSearchReqDTO reqDTO);

}
