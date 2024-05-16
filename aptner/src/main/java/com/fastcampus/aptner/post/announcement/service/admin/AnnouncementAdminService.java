package com.fastcampus.aptner.post.announcement.service.admin;

import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface AnnouncementAdminService {

    ResponseEntity<HttpStatus> uploadAnnouncement(MemberTempDTO.MemberAuthDTO userToken, Long apartmentId, AnnouncementDTO.AnnouncementPostReqDTO dto);
    ResponseEntity<HttpStatus> updateAnnouncement(MemberTempDTO.MemberAuthDTO userToken, Long apartmentId, Long announcementId,AnnouncementDTO.AnnouncementPostReqDTO dto);
    ResponseEntity<HttpStatus> deleteAnnouncement(MemberTempDTO.MemberAuthDTO userToken, Long apartmentId, Long announcementId);
    ResponseEntity<HttpStatus> hideAnnouncement(MemberTempDTO.MemberAuthDTO userToken, Long apartmentId, Long announcementId);
}
