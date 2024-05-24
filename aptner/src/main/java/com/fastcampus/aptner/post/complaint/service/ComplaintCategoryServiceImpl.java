package com.fastcampus.aptner.post.complaint.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.service.ApartmentCommonService;
import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import com.fastcampus.aptner.post.complaint.repository.ComplaintCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ComplaintCategoryServiceImpl implements ComplaintCategoryService {

    private final ComplaintCategoryRepository complaintCategoryRepository;
    private final ApartmentCommonService apartmentCommonService;

    @Override
    public ResponseEntity<?> getComplaintCategoryList(Long apartmentId) {
        Apartment apartment = apartmentCommonService.getApartmentById(apartmentId);
        List<ComplaintDTO.ComplaintCategoryRespDTO> list = complaintCategoryRepository
                .findAllByApartmentId(apartment).stream()
                .map(ComplaintDTO.ComplaintCategoryRespDTO::new)
                .toList();
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
