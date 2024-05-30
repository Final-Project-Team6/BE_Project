package com.fastcampus.aptner.post.information.controller.admin;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import com.fastcampus.aptner.post.information.service.admin.InformationAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/information/admin")
@Tag(name = "정보공간(관리자)", description = "정보글 생성, 정보글 수정, 정보글 삭제")
public class InformationAdminController {

    private final InformationAdminService informationAdminService;

    @Operation(
            summary = "정보글 생성 API",
            description = "Schema -> 정보공간 생성 \n\n apartmentId : 현재 사용중인 아파트 ID "
    )
    @PostMapping(value = "/{apartmentId}")
    public ResponseEntity<HttpStatus> uploadInformation(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long apartmentId,
            @RequestBody InformationDTO.InformationPostReqDTO dto){
        return informationAdminService.uploadInformation(memberToken, apartmentId, dto);
    }

    @Operation(
            summary = "정보글 수정 API",
            description = "Schema -> 정보공간 생성 \n\n informationId : 수정하려는 정보글 ID"
    )
    @PatchMapping(value = "/{informationId}")
    public ResponseEntity<HttpStatus> updateInformation(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long informationId,
            @RequestBody InformationDTO.InformationPostReqDTO dto){
        return informationAdminService.updateInformation(memberToken, informationId, dto);
    }

    @Operation(
            summary = "정보글 삭제 API",
            description = "informationId : 삭제하려는 정보글 ID"
    )
    @DeleteMapping(value = "/{informationId}")
    public ResponseEntity<HttpStatus> deleteCommunication(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long informationId){
        return informationAdminService.deleteInformation(memberToken, informationId);
    }
}
