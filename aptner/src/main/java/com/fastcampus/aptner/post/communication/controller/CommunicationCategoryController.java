package com.fastcampus.aptner.post.communication.controller;

import com.fastcampus.aptner.post.communication.dto.CommunicationDTO;
import com.fastcampus.aptner.post.communication.service.CommunicationCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/communication/category")
@Tag(name = "소통공간 카테고리(사용자)", description = "소통공간 카테고리 조회")
public class CommunicationCategoryController {

    private final CommunicationCategoryService communicationCategoryService;


    @Operation(
            summary = "소통공간 카테고리 조회 API",
            description = "apartmentId : 현재 사용중인 아파트 ID "
    )
    @GetMapping("/{apartmentId}")
    public ResponseEntity<List<CommunicationDTO.CommunicationCategoryRespDTO>> getCommunicationCategoryList(
            @PathVariable Long apartmentId) {
        return communicationCategoryService.getCommunicationCategoryList(apartmentId);
    }

}
