package com.fastcampus.aptner.post.complaint.controller;

import com.fastcampus.aptner.member.domain.RoleName;
import com.fastcampus.aptner.post.complaint.domain.ComplaintStatus;
import com.fastcampus.aptner.post.complaint.service.admin.ComplaintAdminService;
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
@RequestMapping("/api/post/complaint/admin")
@Tag(name = "민원(관리자)", description = "")
public class ComplaintAdminController {

    private final ComplaintAdminService complaintAdminService;
    private MemberTempDTO.MemberAuthDTO memberTempToken = new MemberTempDTO.MemberAuthDTO(1L, RoleName.ADMIN,1L);

    @Operation(
            summary = "민원 진행 상태 변경 API",
            description = "complaintId : 상태 변경 하려는 민원 ID\n\n" +
                    "complaintStatus : 변경 하려는 상태 => SUBMITTED(제출됨), VERIFIED(확인됨), INVESTIGATING(조사중), PROCESSING(처리중), RESPONDED(답변됨), COMPLETED(완료됨)"
    )
    @PatchMapping("{complaintId}")
    public ResponseEntity<HttpStatus> updateComplaintStatus(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO token,
            @PathVariable Long complaintId,
            @RequestParam ComplaintStatus complaintStatus){
        return complaintAdminService.updateComplaintStatus(memberTempToken,complaintId,complaintStatus);
    }
}
