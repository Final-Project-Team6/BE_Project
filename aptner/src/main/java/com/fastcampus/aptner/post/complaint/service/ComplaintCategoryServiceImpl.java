package com.fastcampus.aptner.post.complaint.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.service.ApartmentCommonService;
import com.fastcampus.aptner.post.complaint.domain.ComplaintCategory;
import com.fastcampus.aptner.post.complaint.domain.ComplaintType;
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
    public ResponseEntity<List<ComplaintDTO.ComplaintCategoryRespDTO>> getComplaintCategoryList(Long apartmentId, ComplaintType complaintType) {
        Apartment apartment = apartmentCommonService.getApartmentById(apartmentId);
        List<ComplaintCategory> list ;
        if (complaintType == ComplaintType.MANAGEMENT_OFFICE || complaintType == ComplaintType.RESIDENTS_COMMITTEE){
            list = complaintCategoryRepository.findAllByApartmentIdAndType(apartment,complaintType);
        } else if (complaintType == null) {
            list = complaintCategoryRepository.findAllByApartmentId(apartment);
        }else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<ComplaintDTO.ComplaintCategoryRespDTO> resp =list.stream()
                .map(ComplaintDTO.ComplaintCategoryRespDTO::new)
                .toList();
        if (resp.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
