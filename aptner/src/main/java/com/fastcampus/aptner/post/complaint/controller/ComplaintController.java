package com.fastcampus.aptner.post.complaint.controller;

import com.fastcampus.aptner.member.domain.RoleName;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import com.fastcampus.aptner.post.complaint.service.ComplaintService;
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
@RequestMapping("/api/post/complaint/")
@Tag(name = "민원(사용자)", description = "")
public class ComplaintController {

    private final ComplaintService complaintService;
    //TODO Member 개발 완료시 지우기
    private MemberTempDTO.MemberAuthDTO memberTempToken = new MemberTempDTO.MemberAuthDTO(1L, RoleName.ADMIN,1L);
    @Operation(
            summary = "민원 생성 API",
            description = "Schema -> 민원 생성 \n\n apartmentId : 현재 사용중인 아파트 ID "
    )
    @PostMapping(value ="/{apartmentId}")
    public ResponseEntity<HttpStatus> uploadComplaint(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long apartmentId,
            @RequestBody ComplaintDTO.ComplaintReqDTO dto){
        return  complaintService.uploadComplaint(memberTempToken,apartmentId,dto);
    }

    @Operation(
            summary = "민원 수정 API",
            description = "Schema -> 민원 생성\n\n complaintId : 수정하려는 민원 ID"
    )
    @PatchMapping(value = "/{complaintId}")
    public ResponseEntity<HttpStatus> updateComplaint(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long complaintId,
            @RequestBody ComplaintDTO.ComplaintReqDTO dto){
        return  complaintService.updateComplaint(memberTempToken,complaintId,dto);
    }

    @Operation(
            summary = "민원 삭제 API",
            description = "complaintId : 삭제하려는 민원 ID"
    )
    @DeleteMapping(value = "/{complaintId}")
    public ResponseEntity<HttpStatus> deleteComplaint(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long announcementId){
        return complaintService.deleteComplaint(memberTempToken,announcementId);
    }
    @Operation(
            summary = "민원 조회 API",
            description = "complaintId : 조회하려는 민원 ID"
    )
    @GetMapping("/{complaintId}")
    public ResponseEntity<?> getComplaint(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long complaintId){
        return complaintService.getComplaint(memberToken,complaintId);
    }
}
