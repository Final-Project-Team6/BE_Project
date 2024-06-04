package com.fastcampus.aptner.post.announcement.controller;

import com.fastcampus.aptner.post.announcement.domain.AnnouncementType;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fastcampus.aptner.post.announcement.service.AnnouncementCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/announcement/category")
@Tag(name = "공지사항 카테고리(사용자)", description = "공지사항 카테고리 조회")
public class AnnouncementCategoryController {
    private final AnnouncementCategoryService announcementCategoryService;

    @Operation(
            summary = "공지사항 카테고리 조회 API",
            description = "apartmentId : 현재 사용중인 아파트 ID \n\n" +
                    "announcementType : 공지사항 타입"
    )
    @GetMapping("/{apartmentId}")
    public ResponseEntity<List<AnnouncementDTO.AnnouncementCategoryRespDTO>> getAnnouncementCategoryList(
            @PathVariable Long apartmentId,
            @RequestParam(required = false) AnnouncementType announcementType) {
        return announcementCategoryService.getAnnouncementCategoryList(apartmentId, announcementType);
    }
}
