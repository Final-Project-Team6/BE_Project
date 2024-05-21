package com.fastcampus.aptner.post.complaint.service.admin;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.common.error.PostErrorCode;
import com.fastcampus.aptner.post.complaint.domain.ComplaintCategory;
import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import com.fastcampus.aptner.post.complaint.repository.ComplaintCategoryRepository;
import com.fastcampus.aptner.apartment.service.ApartmentCommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ComplaintCategoryAdminServiceImpl implements ComplaintCategoryAdminService {

    private final ComplaintCategoryRepository complaintCategoryRepository;
    private final ApartmentCommonService apartmentCommonService;


    @Override
    public ResponseEntity<HttpStatus> createComplaintCategory(JWTMemberInfoDTO token, Long apartmentId, ComplaintDTO.ComplaintCategoryReqDTO dto){
        isCorrectApartment(token,apartmentId);
        Apartment apartment = apartmentCommonService.getApartmentById(apartmentId);
        complaintCategoryRepository.save(ComplaintCategory.from(dto,apartment));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateComplaintCategory(JWTMemberInfoDTO token, Long complaintCategoryId ,ComplaintDTO.ComplaintCategoryReqDTO dto){
        ComplaintCategory complaintCategory = complaintCategoryRepository.findById(complaintCategoryId).orElseThrow(NoSuchElementException::new);
        complaintCategory.updateComplaintCategory(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteComplaintCategory(JWTMemberInfoDTO token, Long complaintCategoryId){
        ComplaintCategory complaintCategory = complaintCategoryRepository.findById(complaintCategoryId).orElseThrow(NoSuchElementException::new);
        complaintCategoryRepository.delete(complaintCategory);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private static void isCorrectApartment(JWTMemberInfoDTO userToken, Long apartmentId){
        if (userToken.getApartmentId()!= apartmentId){
            throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
        }
    }
}
