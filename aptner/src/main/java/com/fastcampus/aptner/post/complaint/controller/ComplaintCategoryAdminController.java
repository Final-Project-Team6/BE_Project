package com.fastcampus.aptner.post.complaint.controller;

import com.fastcampus.aptner.member.domain.RoleName;
import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import com.fastcampus.aptner.post.complaint.service.admin.ComplaintCategoryAdminService;
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
@RequestMapping("/api/post/complaint/category/admin")
@Tag(name = "민원 카테고리(관리자)", description = "")
public class ComplaintCategoryAdminController {

    private final ComplaintCategoryAdminService complaintCategoryAdminService;

    //TODO Member 개발 완료시 지우기
    private MemberTempDTO.MemberAuthDTO memberTempToken = new MemberTempDTO.MemberAuthDTO(1L, RoleName.ADMIN,1L);

    @Operation(
            summary = "민원 카테고리 생성 API",
            description = "Schema -> 민원 카테고리 생성 \n\n " +
                    "apartmentId : 현재 사용중인 아파트 ID "
    )
    @PostMapping("/{apartmentId}")
    public ResponseEntity<HttpStatus> createComplaintCategory(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long apartmentId,
            @RequestBody ComplaintDTO.ComplaintCategoryReqDTO dto){
        return complaintCategoryAdminService.createComplaintCategory(memberTempToken,apartmentId,dto);
    }
    @Operation(
            summary = "민원 카테고리 수정 API",
            description = "Schema -> 민원 카테고리 생성 \n\n" +
                    "complaintCategoryId : 수정 하려는 민원 카테고리 ID  "
    )
    @PatchMapping("/{complaintCategoryId}")
    public ResponseEntity<HttpStatus> updateComplaintCategory(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long complaintCategoryId,
            @RequestBody ComplaintDTO.ComplaintCategoryReqDTO dto){
        return complaintCategoryAdminService.updateComplaintCategory(memberTempToken,complaintCategoryId,dto);
    }
    @Operation(
            summary = "민원 카테고리 삭제 API",
            description = "complaintCategoryId : 삭제 하려는 민원 카테고리 ID "
    )
    @DeleteMapping("/{complaintCategoryId}")
    public ResponseEntity<HttpStatus> deleteComplaintCategory(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long complaintCategoryId){
        return complaintCategoryAdminService.deleteComplaintCategory(memberTempToken,complaintCategoryId);
    }

}
