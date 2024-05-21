package com.fastcampus.aptner.post.complaint.service;

import org.springframework.http.ResponseEntity;

public interface ComplaintCategoryService {

    ResponseEntity<?> getComplaintCategoryList(Long apartmentId);
}
