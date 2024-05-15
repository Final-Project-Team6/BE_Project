package com.fastcampus.aptner.post.complaint.dto;

import com.fastcampus.aptner.post.announcement.domain.AnnouncementType;
import com.fastcampus.aptner.post.complaint.domain.ComplaintType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Service;

public class ComplaintDTO {

    @Schema(name = "민원 카테고리 생성",description = "민원 카테고리 생성 요청 Body")
    public record ComplaintCategoryReqDTO(
            @Schema(description = "민원 카테고리 이름")
            String name,
            @Schema(description = "민원 카테고리 타입")
            ComplaintType type) {
    }
}
