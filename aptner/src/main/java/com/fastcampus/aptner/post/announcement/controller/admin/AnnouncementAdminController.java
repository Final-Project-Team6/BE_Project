package com.fastcampus.aptner.post.announcement.controller.admin;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fastcampus.aptner.post.announcement.service.admin.AnnouncementAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/post/announcement")
@Tag(name = "공지사항(관리자)", description = "공지사항 생성, 공지사항 수정, 공지사항 삭제, 공지사항 숨기기")
public class AnnouncementAdminController {
    private final AnnouncementAdminService announcementService;

    @Operation(
            summary = "공지사항 생성 API",
            description = "Schema -> 공지사항 생성 \n\n apartmentId : 현재 사용중인 아파트 ID "
    )
    @PostMapping(value = "/{apartmentId}")
    public ResponseEntity<HttpStatus> uploadAnnouncement(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long apartmentId,
            @RequestBody AnnouncementDTO.AnnouncementPostReqDTO dto) {
        return announcementService.uploadAnnouncement(memberToken, apartmentId, dto);
    }

    @Operation(
            summary = "공지사항 수정 API",
            description = "Schema -> 공지사항 생성\n\n apartmentId : 현재 사용중인 아파트 ID\n\n announcementId : 수정하려는 공지사항 ID"
    )
    @PatchMapping(value = "/{announcementId}")
    public ResponseEntity<HttpStatus> updateAnnouncement(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long announcementId,
            @RequestBody AnnouncementDTO.AnnouncementPostReqDTO dto) {
        return announcementService.updateAnnouncement(memberToken, announcementId, dto);
    }

    @Operation(
            summary = "공지사항 삭제 API",
            description = "apartmentId : 현재 사용중인 아파트 ID\n\n announcementId : 삭제하려는 공지사항 ID"
    )
    @DeleteMapping(value = "/{announcementId}")
    public ResponseEntity<HttpStatus> deleteAnnouncement(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long announcementId) {
        return announcementService.deleteAnnouncement(memberToken, announcementId);
    }

    @Operation(
            summary = "공지사항 숨기기 API",
            description = "apartmentId : 현재 사용중인 아파트 ID\n\n announcementId : 숨기려는 공지사항 ID"
    )
    @PatchMapping(value = "/hide/{announcementId}")
    public ResponseEntity<HttpStatus> hideAnnouncement(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long announcementId) {
        return announcementService.hideAnnouncement(memberToken, announcementId);
    }

}
