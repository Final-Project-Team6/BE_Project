package com.fastcampus.aptner.post.information.controller.admin;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import com.fastcampus.aptner.post.information.service.admin.InformationCategoryAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/information/category/admin")
@Tag(name = "정보공간 카테고리(관리자)", description = "정보공간 카테고리 생성, 수정, 삭제")
public class InformationCategoryAdminController {

    private final InformationCategoryAdminService informationCategoryAdminService;

    @Operation(
            summary = "정보공간 카테고리 생성 API",
            description = "Schema -> 정보공간 카테고리 생성 \n\n apartmentId : 현재 사용중인 아파트 ID "
    )
    @PostMapping(value = "/{apartmentId}")
    public ResponseEntity<HttpStatus> createCategory(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long apartmentId,
            @RequestBody InformationDTO.InformationCategoryReqDTO dto){
        return informationCategoryAdminService.createInformationCategory(memberToken,apartmentId,dto);
    }

    @Operation(
            summary = "정보공간 카테고리 수정 API",
            description = "Schema -> 정보공간 카테고리 수정 \n\n apartmentId : 현재 사용중인 아파트 ID\n\n informationCategoryId : 수정하려는 정보공간 카테고리 ID"
    )
    @PatchMapping(value = "/{informationCategoryId}")
    public ResponseEntity<HttpStatus> updateInformationCategory(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long informationCategoryId,
            @RequestBody InformationDTO.InformationCategoryReqDTO dto){
        return informationCategoryAdminService.updateInformationCategory(memberToken,informationCategoryId,dto);
    }

    @Operation(
            summary = "정보공간 카테고리 삭제 API",
            description = "apartmentId : 현재 사용중인 아파트 ID\n\n informationCategoryId : 삭제하려는 정보공간 ID"
    )
    @DeleteMapping(value ="/{informationCategoryId}")
    public ResponseEntity<HttpStatus> deleteCommunicationCategory(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long informationCategoryId){
        return  informationCategoryAdminService.deleteInformationCategory(memberToken,informationCategoryId);
    }
}
