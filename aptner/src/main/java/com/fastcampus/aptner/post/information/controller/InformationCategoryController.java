package com.fastcampus.aptner.post.information.controller;

import com.fastcampus.aptner.post.information.dto.InformationDTO;
import com.fastcampus.aptner.post.information.service.InformationCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/information/category")
@Tag(name = "정보공간 카테고리(사용자)", description = "정보공간 카테고리 조회")
public class InformationCategoryController {

   private final InformationCategoryService informationCategoryService;

   @Operation(
            summary = "정보공간 카테고리 조회 API",
            description = "apartmentId : 현재 사용중인 아파트 ID "
    )
    @GetMapping("/{apartmentId}")
    public ResponseEntity<List<InformationDTO.InformationCategoryRespDTO>> getInformationCategoryList(
            @PathVariable Long apartmentId) {
        return informationCategoryService.getInformationCategoryList(apartmentId);
    }
}
