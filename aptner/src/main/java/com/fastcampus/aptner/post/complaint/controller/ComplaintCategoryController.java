package com.fastcampus.aptner.post.complaint.controller;

import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import com.fastcampus.aptner.post.complaint.service.ComplaintCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/complaint/category/")
@Tag(name = "민원 카테고리(사용자)", description = "")
public class ComplaintCategoryController {

    private final ComplaintCategoryService complaintCategoryService;

    @Operation(
            summary = "민원 카테고리 조회 API",
            description = "apartmentId : 현재 사용중인 아파트 ID "
    )
    @GetMapping("{apartmentId}")
    public ResponseEntity<List<ComplaintDTO.ComplaintCategoryRespDTO>> getComplaintCategoryList(
            @PathVariable Long apartmentId) {
        return complaintCategoryService.getComplaintCategoryList(apartmentId);
    }
}
