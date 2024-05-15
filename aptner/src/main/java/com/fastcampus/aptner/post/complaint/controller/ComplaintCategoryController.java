package com.fastcampus.aptner.post.complaint.controller;

import com.fastcampus.aptner.post.complaint.service.admin.ComplaintCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/complaint/category/")
@Tag(name = "민원 카테고리(사용자)", description = "")
public class ComplaintCategoryController {

    private final ComplaintCategoryService complaintCategoryService;

    @GetMapping("{apartmentId}")
    public ResponseEntity<?> getComplaintCategoryList(
            @PathVariable Long apartmentId){
        return complaintCategoryService.getComplaintCategoryList(apartmentId);
    }
}
