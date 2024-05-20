package com.fastcampus.aptner.post.information.controller;

import com.fastcampus.aptner.member.domain.RoleName;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import com.fastcampus.aptner.post.information.service.InformationCategoryService;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
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

    //TODO 지우기
    private MemberTempDTO.MemberAuthDTO memberTempToken = new MemberTempDTO.MemberAuthDTO(1L, RoleName.ADMIN,1L);


    @PostMapping(value = "/{apartmentId}")
    public ResponseEntity<HttpStatus> createCategory(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long apartmentId,
            @RequestBody InformationDTO.InformationCategoryReqDTO dto){
        return informationCategoryService.createInformationCategory(memberTempToken,apartmentId,dto);
    }

    @PatchMapping(value = "/{informationCategoryId}")
    public ResponseEntity<HttpStatus> updateInformationCategory(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long informationCategoryId,
            @RequestBody InformationDTO.InformationCategoryReqDTO dto){
        return informationCategoryService.updateInformationCategory(memberTempToken,informationCategoryId,dto);
    }

    @DeleteMapping(value ="/{informationCategoryId}")
    public ResponseEntity<HttpStatus> deleteCommunicationCategory(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long informationCategoryId){
        return  informationCategoryService.deleteInformationCategory(memberTempToken,informationCategoryId);
    }

}
