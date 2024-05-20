package com.fastcampus.aptner.post.communication.controller;

import com.fastcampus.aptner.member.domain.RoleName;
import com.fastcampus.aptner.post.communication.dto.CommunicationDTO;
import com.fastcampus.aptner.post.communication.service.CommunicationCategoryService;
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
@RequestMapping("/api/post/announcement/category")
@Tag(name = "소통공간", description = "소통공간 카테고리 생성, 수정, 삭제")
public class CommunicationCategoryController {

    private final CommunicationCategoryService communicationCategoryService;

    //TODO 지우기
    private MemberTempDTO.MemberAuthDTO memberTempToken = new MemberTempDTO.MemberAuthDTO(1L, RoleName.ADMIN,1L);

    @Operation(
            summary = "소통공간 카테고리 생성 API",
            description = "Schema -> 소통공간 카테고리 생성 \n\n apartmentId : 현재 사용중인 아파트 ID "
    )
    @PostMapping(value = "/{apartmentId}")
    public ResponseEntity<HttpStatus> createCategory(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long apartmentId,
            @RequestBody CommunicationDTO.CommunicationCategoryReqDTO dto){
        return communicationCategoryService.createCommunicationCategory(memberTempToken,apartmentId,dto);
    }

    @Operation(
            summary = "소통공간 카테고리 수정 API",
            description = "Schema -> 소통공간 카테고리 수정 \n\n apartmentId : 현재 사용중인 아파트 ID\n\n communicationCategoryId : 수정하려는 소통공간 카테고리 ID"
    )
    @PatchMapping(value = "/{communicationCategoryId}")
    public ResponseEntity<HttpStatus> updateCommunicationCategory(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long communicationCategoryId,
            @RequestBody CommunicationDTO.CommunicationCategoryReqDTO dto){
        return communicationCategoryService.updateCommunicationCategory(memberTempToken,communicationCategoryId,dto);
    }

    @Operation(
            summary = "소통공간 카테고리 삭제 API",
            description = "apartmentId : 현재 사용중인 아파트 ID\n\n communicationCategoryId : 삭제하려는 소통공간 ID"
    )
    @DeleteMapping(value ="/{communicationCategoryId}")
    public ResponseEntity<HttpStatus> deleteCommunicationCategory(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long communicationCategoryId){
        return  communicationCategoryService.deleteCommunicationCategory(memberTempToken,communicationCategoryId);
    }
}
