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

import java.util.Objects;

import static com.fastcampus.aptner.global.error.CommonErrorCode.MUST_AUTHORIZE;
import static com.fastcampus.aptner.post.common.error.PostErrorCode.*;

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
        if(userToken==null) throw new RestAPIException(MUST_AUTHORIZE);
        UserAndAPT userAndAPT = getUserAndAPT(userToken,apartmentId);
        InformationCategory informationCategory = informationCategoryRepository.findById(dto.informationCategoryId()).orElseThrow(()->new RestAPIException(NO_SUCH_CATEGORY));
        informationRepository.save(Information.from(userAndAPT.member,informationCategory,dto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateInformation(JWTMemberInfoDTO userToken, Long informationId, InformationDTO.InformationPostReqDTO dto) {
        Information information = informationRepository.findById(informationId).orElseThrow(()->new RestAPIException(NO_SUCH_POST));
        checkMemberByInformation(userToken,information);
        InformationCategory informationCategory = informationCategoryRepository.findById(dto.informationCategoryId()).orElseThrow(()->new RestAPIException(NO_SUCH_CATEGORY));
        information.updateInformation(informationCategory,dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteInformation(JWTMemberInfoDTO userToken, Long informationId) {
        Information information = informationRepository.findById(informationId).orElseThrow(()->new RestAPIException(NO_SUCH_POST));
        checkMemberByInformation(userToken,information);
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
    private void checkMemberByInformation(JWTMemberInfoDTO userToken,Information information){
        if (userToken==null) {
            throw new RestAPIException(MUST_AUTHORIZE);
        }
        if (!Objects.equals(userToken.getMemberId(), information.getMemberId().getMemberId())){
            throw new RestAPIException(NOT_SAME_USER);
        }
    }
    /*
    //deleteInformation에서 checkMemberByInformation 대신 넣을거 - 여긴 전부다 관리자만 권한이 있는 부분이라,,,
    //일단 일반 컨트롤러에 전부 기능 구현하고 관리자 기능 따로 만드는 식으로 해놔야 할듯
    private static void requestHasRole(JWTMemberInfoDTO userToken, Information information) {
        if (userToken == null) {
            throw new RestAPIException(MUST_AUTHORIZE);
        }
        if (userToken.getMemberId() != information.getMemberId().getMemberId()) {
            if (userToken.getRoleName().equals("ADMIN")) {
                throw new RestAPIException(NOT_SAME_USER);
            }
            if (userToken.getApartmentId() != information.getInformationCategoryId().getApartmentId().getApartmentId()) {
                throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
            }
        }
    }
    */

}
