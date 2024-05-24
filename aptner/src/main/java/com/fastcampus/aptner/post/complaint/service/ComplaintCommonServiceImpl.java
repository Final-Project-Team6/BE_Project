package com.fastcampus.aptner.post.complaint.service;

import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.complaint.domain.ComplaintCategory;
import com.fastcampus.aptner.post.complaint.repository.ComplaintCategoryRepository;
import com.fastcampus.aptner.post.complaint.repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static com.fastcampus.aptner.post.common.error.PostErrorCode.NO_SUCH_CATEGORY;
import static com.fastcampus.aptner.post.common.error.PostErrorCode.NO_SUCH_POST;

@Service
@Slf4j
@RequiredArgsConstructor
public class ComplaintCommonServiceImpl implements ComplaintCommonService {

    private final ComplaintCategoryRepository complaintCategoryRepository;
    private final ComplaintRepository complaintRepository;

    @Override
    public Complaint getComplaintEntity(Long complaintId) {
        return complaintRepository.findById(complaintId).orElseThrow(()->new RestAPIException(NO_SUCH_POST));
    }

    @Override
    public ComplaintCategory getComplaintCategoryEntity(Long complaintCategoryId) {
        return complaintCategoryRepository.findById(complaintCategoryId).orElseThrow(()->new RestAPIException(NO_SUCH_CATEGORY));
    }
}
