package com.fastcampus.aptner.post.complaint.service;

import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.complaint.domain.ComplaintCategory;

public interface ComplaintCommonService {

    Complaint getComplaintEntity(Long complaintId);
    ComplaintCategory getComplaintCategoryEntity(Long complaintCategoryId);
}
