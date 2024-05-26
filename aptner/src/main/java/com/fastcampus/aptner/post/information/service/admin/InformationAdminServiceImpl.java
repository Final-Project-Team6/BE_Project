package com.fastcampus.aptner.post.information.service.admin;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.common.error.PostErrorCode;
import com.fastcampus.aptner.post.information.domain.Information;
import com.fastcampus.aptner.post.information.domain.InformationCategory;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import com.fastcampus.aptner.post.information.repository.InformationCategoryRepository;
import com.fastcampus.aptner.post.information.repository.InformationRepository;
import com.fastcampus.aptner.apartment.service.ApartmentCommonService;
import com.fastcampus.aptner.member.service.MemberCommonService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InformationAdminServiceImpl implements InformationAdminService {
    InformationRepository informationRepository;
    InformationCategoryRepository informationCategoryRepository;

    MemberCommonService memberCommonService;
    ApartmentCommonService apartmentCommonService;

    @Override
    public ResponseEntity<HttpStatus> uploadInformation(JWTMemberInfoDTO userToken, Long apartmentId, InformationDTO.InformationPostReqDTO dto) {
        UserAndAPT userAndAPT = getUserAndAPT(userToken,apartmentId);
        InformationCategory informationCategory = informationCategoryRepository.findById(dto.informationCategoryId()).get();
        Information information = Information.from(userAndAPT.member,informationCategory,dto);
        //Todo 예외처리
        informationRepository.save(information);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateInformation(JWTMemberInfoDTO userToken, Long informationId, InformationDTO.InformationPostReqDTO dto) {
        Information information = informationRepository.findById(informationId).orElseThrow(NoSuchElementException::new);
        checkApartmentByAnnouncement(userToken,information);
        InformationCategory informationCategory = informationCategoryRepository.findById(dto.informationCategoryId()).get();
        //Todo 권한 확인
        //Todo 예외처리
        information.updateInformation(informationCategory,dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteInformation(JWTMemberInfoDTO userToken, Long informationId) {
        Information information = informationRepository.findById(informationId).orElseThrow(NoSuchElementException::new);
        checkApartmentByAnnouncement(userToken,information);
        //Todo 권한 확인
        //Todo 예외처리
        information.deleteInformation();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @AllArgsConstructor
    public static class UserAndAPT{
        Member member;
        Apartment apartment;
    }
    private UserAndAPT getUserAndAPT(JWTMemberInfoDTO userToken, Long apartmentId){
        Member member = memberCommonService.getUserByToken(userToken);
        if (!Objects.equals(userToken.getApartmentId(), apartmentId)){
            throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
        }
        Apartment apartment = apartmentCommonService.getApartmentById(apartmentId);
        return new UserAndAPT(member, apartment);
    }
    private void checkApartmentByAnnouncement(JWTMemberInfoDTO userToken,Information information){
        if (!Objects.equals(userToken.getApartmentId(), information.getInformationCategoryId().getApartmentId().getApartmentId())){
            throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
        }
    }

}
