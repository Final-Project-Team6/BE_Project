package com.fastcampus.aptner.post.complaint.service;

import com.fastcampus.aptner.post.complaint.domain.ComplaintType;
import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ComplaintCategoryService {

    ResponseEntity<List<ComplaintDTO.ComplaintCategoryRespDTO>> getComplaintCategoryList(Long apartmentId, ComplaintType complaintType);
}
