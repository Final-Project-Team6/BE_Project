package com.fastcampus.aptner.post.complaint.controller;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import com.fastcampus.aptner.post.complaint.service.admin.ComplaintCategoryAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/post/complaint/category")
@Tag(name = "민원 카테고리(관리자)", description = "")
public class ComplaintCategoryAdminController {

    private final ComplaintCategoryAdminService complaintCategoryAdminService;

    @Operation(
            summary = "민원 카테고리 생성 API",
            description = "Schema -> 민원 카테고리 생성 \n\n " +
                    "apartmentId : 현재 사용중인 아파트 ID "
    )
    @PostMapping("/{apartmentId}")
    public ResponseEntity<HttpStatus> createComplaintCategory(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long apartmentId,
            @RequestBody ComplaintDTO.ComplaintCategoryReqDTO dto) {
        return complaintCategoryAdminService.createComplaintCategory(memberToken, apartmentId, dto);
    }

    @Operation(
            summary = "민원 카테고리 수정 API",
            description = "Schema -> 민원 카테고리 생성 \n\n" +
                    "complaintCategoryId : 수정 하려는 민원 카테고리 ID  "
    )
    @PatchMapping("/{complaintCategoryId}")
    public ResponseEntity<HttpStatus> updateComplaintCategory(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long complaintCategoryId,
            @RequestBody ComplaintDTO.ComplaintCategoryReqDTO dto) {
        return complaintCategoryAdminService.updateComplaintCategory(memberToken, complaintCategoryId, dto);
    }

    @Operation(
            summary = "민원 카테고리 삭제 API",
            description = "complaintCategoryId : 삭제 하려는 민원 카테고리 ID "
    )
    @DeleteMapping("/{complaintCategoryId}")
    public ResponseEntity<HttpStatus> deleteComplaintCategory(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long complaintCategoryId) {
        return complaintCategoryAdminService.deleteComplaintCategory(memberToken, complaintCategoryId);
    }

}
