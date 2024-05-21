package com.fastcampus.aptner.post.announcement.service;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementCategory;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementType;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fastcampus.aptner.post.common.dto.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AnnouncementService {
    ResponseEntity<PageResponseDTO> searchAnnouncement(AnnouncementDTO.AnnouncementSearchReqDTO reqDTO);

    ResponseEntity<AnnouncementDTO.AnnouncementRespDTO> getAnnouncement(Long announcementId, JWTMemberInfoDTO request);

}
