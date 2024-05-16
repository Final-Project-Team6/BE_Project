package com.fastcampus.aptner.post.announcement.service.admin;


import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface AnnouncementCategoryAdminService {

    ResponseEntity<HttpStatus> createAnnouncementCategory(MemberTempDTO.MemberAuthDTO userToken , Long apartmentId, AnnouncementDTO.AnnouncementCategoryReqDTO dto);
    ResponseEntity<HttpStatus> updateAnnouncementCategory(MemberTempDTO.MemberAuthDTO userToken ,Long announcementCategoryId, AnnouncementDTO.AnnouncementCategoryReqDTO dto);
    ResponseEntity<HttpStatus> deleteAnnouncementCategory(MemberTempDTO.MemberAuthDTO userToken , Long announcementCategoryId);
}
