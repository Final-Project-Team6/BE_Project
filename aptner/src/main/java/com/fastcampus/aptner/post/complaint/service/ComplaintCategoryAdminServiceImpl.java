package com.fastcampus.aptner.post.complaint.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.post.common.error.PostErrorCode;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.complaint.domain.ComplaintCategory;
import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import com.fastcampus.aptner.post.complaint.repository.ComplaintCategoryRepository;
import com.fastcampus.aptner.post.complaint.service.admin.ComplaintCategoryAdminService;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import com.fastcampus.aptner.post.temp.service.ApartmentCommonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComplaintCategoryAdminServiceImpl implements ComplaintCategoryAdminService {

    private final ComplaintCategoryRepository complaintCategoryRepository;
    private final ApartmentCommonService apartmentCommonService;


    @Override
    public ResponseEntity<HttpStatus> createComplaintCategory(MemberTempDTO.MemberAuthDTO token, Long apartmentId, ComplaintDTO.ComplaintCategoryReqDTO dto){
        isCorrectApartment(token,apartmentId);
        Apartment apartment = apartmentCommonService.getApartmentById(apartmentId);
        complaintCategoryRepository.save(ComplaintCategory.from(dto,apartment));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateComplaintCategory(MemberTempDTO.MemberAuthDTO token, Long complaintCategoryId ,ComplaintDTO.ComplaintCategoryReqDTO dto){
        ComplaintCategory complaintCategory = complaintCategoryRepository.findById(complaintCategoryId).orElseThrow(NoSuchElementException::new);
        complaintCategory.updateComplaintCategory(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteComplaintCategory(MemberTempDTO.MemberAuthDTO token, Long complaintCategoryId){
        ComplaintCategory complaintCategory = complaintCategoryRepository.findById(complaintCategoryId).orElseThrow(NoSuchElementException::new);
        complaintCategoryRepository.delete(complaintCategory);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private static void isCorrectApartment(MemberTempDTO.MemberAuthDTO userToken, Long apartmentId){
        if (userToken.ApartmentId()!= apartmentId){
            throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
        }
    }
}
