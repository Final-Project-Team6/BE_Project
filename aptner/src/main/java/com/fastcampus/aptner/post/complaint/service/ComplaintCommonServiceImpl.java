package com.fastcampus.aptner.post.complaint.service;

import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.complaint.domain.ComplaintCategory;
import com.fastcampus.aptner.post.complaint.repository.ComplaintCategoryRepository;
import com.fastcampus.aptner.post.complaint.repository.ComplaintRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComplaintCommonServiceImpl implements ComplaintCommonService{

    private final ComplaintCategoryRepository complaintCategoryRepository;
    private final ComplaintRepository complaintRepository;

    @Override
    public Complaint getComplaintEntity(Long complaintId){
        return complaintRepository.findById(complaintId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public ComplaintCategory getComplaintCategoryEntity(Long complaintCategoryId){
        return complaintCategoryRepository.findById(complaintCategoryId).orElseThrow(NoSuchElementException::new);
    }
}
