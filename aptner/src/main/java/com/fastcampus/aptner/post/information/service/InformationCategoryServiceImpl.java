package com.fastcampus.aptner.post.information.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.repository.ApartmentRepository;
import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.common.error.PostErrorCode;
import com.fastcampus.aptner.post.information.domain.InformationCategory;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import com.fastcampus.aptner.post.information.repository.InformationCategoryRepository;
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
public class InformationCategoryServiceImpl implements InformationCategoryService {

    InformationCategoryRepository informationCategoryRepository;
    ApartmentRepository apartmentRepository;

    @Override
    public ResponseEntity<HttpStatus> createInformationCategory(JWTMemberInfoDTO userToken, Long apartmentId, InformationDTO.InformationCategoryReqDTO dto) {
        isCorrectApartment(userToken,apartmentId);
        Apartment apartment = apartmentRepository.findApartmentByApartmentId(apartmentId).get();
        informationCategoryRepository.save(InformationCategory.from(apartment,dto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateInformationCategory(JWTMemberInfoDTO userToken, Long informationCategoryId, InformationDTO.InformationCategoryReqDTO dto) {
        InformationCategory informationCategory = informationCategoryRepository.findById(informationCategoryId).orElseThrow(NoSuchElementException::new);
        checkApartmentByAnnouncementCategory(userToken,informationCategory);
        informationCategory.updateCategory(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteInformationCategory(JWTMemberInfoDTO userToken, Long InformationCategoryId) {

        InformationCategory informationCategory = informationCategoryRepository.findById(InformationCategoryId).orElseThrow(NoSuchElementException::new);
        checkApartmentByAnnouncementCategory(userToken,informationCategory);
        if(!informationCategory.getInformationList().isEmpty())
            throw new RestAPIException(CANT_DELETE);
        informationCategoryRepository.delete(informationCategory);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static void isCorrectApartment(JWTMemberInfoDTO userToken, Long apartmentId){
        if (userToken.getApartmentId()!= apartmentId){
            throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
        }
    }

    private void checkApartmentByAnnouncementCategory(JWTMemberInfoDTO userToken, InformationCategory informationCategory){
        if (!Objects.equals(userToken.getApartmentId(), informationCategory.getApartmentId().getApartmentId())){
            throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
        }
    }

}
