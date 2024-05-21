package com.fastcampus.aptner.post.announcement.service.admin;


import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface AnnouncementCategoryAdminService {

    ResponseEntity<HttpStatus> createAnnouncementCategory(JWTMemberInfoDTO userToken , Long apartmentId, AnnouncementDTO.AnnouncementCategoryReqDTO dto);
    ResponseEntity<HttpStatus> updateAnnouncementCategory(JWTMemberInfoDTO userToken ,Long announcementCategoryId, AnnouncementDTO.AnnouncementCategoryReqDTO dto);
    ResponseEntity<HttpStatus> deleteAnnouncementCategory(JWTMemberInfoDTO userToken , Long announcementCategoryId);
}
