package com.fastcampus.aptner.post.announcement.controller.admin;

import com.fastcampus.aptner.member.domain.RoleName;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fastcampus.aptner.post.announcement.service.admin.AnnouncementCategoryAdminService;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/announcement/category/admin")
@Tag(name = "공지사항 카테고리(관리자)", description = "공지사항 카테고리 생성, 수정, 삭제")
public class AnnouncementCategoryAdminController {

    private final AnnouncementCategoryAdminService announcementCategoryAdminService;

    //TODO 지우기
    private MemberTempDTO.MemberAuthDTO memberTempToken = new MemberTempDTO.MemberAuthDTO(1L, RoleName.ADMIN,1L);
    @Operation(
            summary = "공지사항 카테고리 생성 API",
            description = "Schema -> 공지사항 카테고리 생성 \n\n apartmentId : 현재 사용중인 아파트 ID "
    )
    @PostMapping(value ="/{apartmentId}")
    public ResponseEntity<HttpStatus> createAnnouncementCategory(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long apartmentId,
            @RequestBody AnnouncementDTO.AnnouncementCategoryReqDTO dto){
        return  announcementCategoryAdminService.createAnnouncementCategory(memberTempToken,apartmentId,dto);
    }
    @Operation(
            summary = "공지사항 카테고리 수정 API",
            description = "Schema -> 공지사항 카테고리 수정 \n\n apartmentId : 현재 사용중인 아파트 ID\n\n announcementCategoryId : 수정하려는 공지사항 카테고리 ID"
    )
    @PatchMapping(value ="/{announcementCategoryId}")
    public ResponseEntity<HttpStatus> updateAnnouncementCategory(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long announcementCategoryId,
            @RequestBody AnnouncementDTO.AnnouncementCategoryReqDTO dto){
        return  announcementCategoryAdminService.updateAnnouncementCategory(memberTempToken,announcementCategoryId,dto);
    }
    @Operation(
            summary = "공지사항 카테고리 삭제 API",
            description = "apartmentId : 현재 사용중인 아파트 ID\n\n announcementId : 삭제하려는 공지사항 ID"
    )
    @DeleteMapping(value ="/{announcementCategoryId}")
    public ResponseEntity<HttpStatus> deleteAnnouncementCategory(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long announcementCategoryId){
        return  announcementCategoryAdminService.deleteAnnouncementCategory(memberTempToken,announcementCategoryId);
    }

}
