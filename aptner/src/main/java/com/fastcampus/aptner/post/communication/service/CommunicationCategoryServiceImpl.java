package com.fastcampus.aptner.post.communication.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.repository.ApartmentRepository;
import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.common.error.PostErrorCode;
import com.fastcampus.aptner.post.communication.domain.CommunicationCategory;
import com.fastcampus.aptner.post.communication.dto.CommunicationDTO;
import com.fastcampus.aptner.post.communication.repository.CommunicationCategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;

import static com.fastcampus.aptner.post.common.error.PostErrorCode.CANT_DELETE;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommunicationCategoryServiceImpl implements CommunicationCategoryService {

    CommunicationCategoryRepository communicationCategoryRepository;

    ApartmentRepository apartmentRepository;

    @Override
    public ResponseEntity<HttpStatus> createCommunicationCategory(JWTMemberInfoDTO userToken, Long apartmentId, CommunicationDTO.CommunicationCategoryReqDTO dto) {
        isCorrectApartment(userToken,apartmentId);
        Apartment apartment = apartmentRepository.findApartmentByApartmentId(apartmentId).get();
        communicationCategoryRepository.save(CommunicationCategory.from(apartment,dto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateCommunicationCategory(JWTMemberInfoDTO userToken, Long communicationCategoryId, CommunicationDTO.CommunicationCategoryReqDTO dto) {
        CommunicationCategory communicationCategory = communicationCategoryRepository.findById(communicationCategoryId).orElseThrow(NoSuchElementException::new);
        checkApartmentByAnnouncementCategory(userToken,communicationCategory);
        communicationCategory.updateCategory(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteCommunicationCategory(JWTMemberInfoDTO userToken, Long CommunicationCategoryId) {
        CommunicationCategory communicationCategory = communicationCategoryRepository.findById(CommunicationCategoryId).orElseThrow(NoSuchElementException::new);
        checkApartmentByAnnouncementCategory(userToken,communicationCategory);
        if(!communicationCategory.getCommunicationList().isEmpty())
            throw new RestAPIException(CANT_DELETE);
        communicationCategoryRepository.delete(communicationCategory);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    private static void isCorrectApartment(JWTMemberInfoDTO userToken, Long apartmentId){
        if (userToken.getApartmentId()!= apartmentId){
            throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
        }
    }

    private void checkApartmentByAnnouncementCategory(JWTMemberInfoDTO userToken, CommunicationCategory communicationCategory){
        if (!Objects.equals(userToken.getApartmentId(), communicationCategory.getApartmentId().getApartmentId())){
            throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
        }
    }


}
