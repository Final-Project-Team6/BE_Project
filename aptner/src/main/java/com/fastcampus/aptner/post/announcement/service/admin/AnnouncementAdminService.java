package com.fastcampus.aptner.post.announcement.service.admin;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface AnnouncementAdminService {

    ResponseEntity<HttpStatus> uploadAnnouncement(JWTMemberInfoDTO userToken, Long apartmentId, AnnouncementDTO.AnnouncementPostReqDTO dto);

    ResponseEntity<HttpStatus> updateAnnouncement(JWTMemberInfoDTO userToken, Long announcementId, AnnouncementDTO.AnnouncementPostReqDTO dto);

    ResponseEntity<HttpStatus> deleteAnnouncement(JWTMemberInfoDTO userToken, Long announcementId);

    ResponseEntity<HttpStatus> hideAnnouncement(JWTMemberInfoDTO userToken, Long announcementId);
}
