package com.fastcampus.aptner.post.information.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.common.error.PostErrorCode;
import com.fastcampus.aptner.post.information.domain.Information;
import com.fastcampus.aptner.post.information.domain.InformationCategory;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import com.fastcampus.aptner.post.information.repository.InformationCategoryRepository;
import com.fastcampus.aptner.post.information.repository.InformationRepository;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import com.fastcampus.aptner.post.temp.repository.TempMemberRepository;
import com.fastcampus.aptner.post.temp.service.ApartmentCommonService;
import com.fastcampus.aptner.post.temp.service.MemberCommonService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InformationServiceImpl implements InformationService {
    InformationRepository informationRepository;
    TempMemberRepository tempMemberRepository;
    InformationCategoryRepository informationCategoryRepository;

    MemberCommonService memberCommonService;
    ApartmentCommonService apartmentCommonService;

    @Override
    public ResponseEntity<HttpStatus> uploadInformation(MemberTempDTO.MemberAuthDTO userToken, Long apartmentId, InformationDTO.InformationPostReqDTO dto) {
        UserAndAPT userAndAPT = getUserAndAPT(userToken,apartmentId);
        InformationCategory informationCategory = informationCategoryRepository.findById(dto.informationCategoryId()).get();
        Information information = Information.from(userAndAPT.member,informationCategory,dto);
        //Todo 예외처리
        informationRepository.save(information);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateInformation(MemberTempDTO.MemberAuthDTO userToken, Long informationId, InformationDTO.InformationPostReqDTO dto) {
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
    public ResponseEntity<HttpStatus> deleteInformation(MemberTempDTO.MemberAuthDTO userToken, Long informationId) {
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
    private UserAndAPT getUserAndAPT(MemberTempDTO.MemberAuthDTO userToken, Long apartmentId){
        Member member = memberCommonService.getUserByToken(userToken);
        if (!Objects.equals(userToken.ApartmentId(), apartmentId)){
            throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
        }
        Apartment apartment = apartmentCommonService.getApartmentById(apartmentId);
        return new UserAndAPT(member, apartment);
    }
    private void checkApartmentByAnnouncement(MemberTempDTO.MemberAuthDTO userToken,Information information){
        if (!Objects.equals(userToken.ApartmentId(), information.getInformationCategoryId().getApartmentId().getApartmentId())){
            throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
        }
    }

}
