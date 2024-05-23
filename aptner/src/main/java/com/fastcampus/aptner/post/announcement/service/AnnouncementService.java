package com.fastcampus.aptner.post.announcement.service;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fastcampus.aptner.post.common.dto.PageResponseDTO;
import org.springframework.http.ResponseEntity;

public interface AnnouncementService {
    ResponseEntity<PageResponseDTO> searchAnnouncement(AnnouncementDTO.AnnouncementSearchReqDTO reqDTO);

    ResponseEntity<AnnouncementDTO.AnnouncementRespDTO> getAnnouncement(Long announcementId, JWTMemberInfoDTO request);

}
