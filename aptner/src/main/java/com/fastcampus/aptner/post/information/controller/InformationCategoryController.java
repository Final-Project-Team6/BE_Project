package com.fastcampus.aptner.post.information.controller;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.domain.RoleName;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import com.fastcampus.aptner.post.information.service.InformationCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/information/category")
@Tag(name = "정보공간", description = "정보공간 카테고리 생성, 수정, 삭제")
public class InformationCategoryController {
    private final InformationCategoryService informationCategoryService;



    @PostMapping(value = "/{apartmentId}")
    public ResponseEntity<HttpStatus> createCategory(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long apartmentId,
            @RequestBody InformationDTO.InformationCategoryReqDTO dto){
        return informationCategoryService.createInformationCategory(memberToken,apartmentId,dto);
    }

    @PatchMapping(value = "/{informationCategoryId}")
    public ResponseEntity<HttpStatus> updateInformationCategory(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long informationCategoryId,
            @RequestBody InformationDTO.InformationCategoryReqDTO dto){
        return informationCategoryService.updateInformationCategory(memberToken,informationCategoryId,dto);
    }

    @DeleteMapping(value ="/{informationCategoryId}")
    public ResponseEntity<HttpStatus> deleteCommunicationCategory(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long informationCategoryId){
        return  informationCategoryService.deleteInformationCategory(memberToken,informationCategoryId);
    }

}
