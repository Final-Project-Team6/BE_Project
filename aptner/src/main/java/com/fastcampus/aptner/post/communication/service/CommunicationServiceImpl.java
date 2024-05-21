package com.fastcampus.aptner.post.communication.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.member.repository.MemberRepository;
import com.fastcampus.aptner.post.common.dto.PageResponseDTO;
import com.fastcampus.aptner.post.common.error.PostErrorCode;
import com.fastcampus.aptner.post.communication.domain.Communication;
import com.fastcampus.aptner.post.communication.domain.CommunicationCategory;
import com.fastcampus.aptner.post.communication.dto.CommunicationDTO;
import com.fastcampus.aptner.post.communication.repository.CommunicationCategoryRepository;
import com.fastcampus.aptner.post.communication.repository.CommunicationRepository;
import com.fastcampus.aptner.apartment.service.ApartmentCommonService;
import com.fastcampus.aptner.member.service.MemberCommonService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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
public class CommunicationServiceImpl implements CommunicationService {

    CommunicationRepository communicationRepository;
    MemberRepository memberRepository;
    CommunicationCategoryRepository communicationCategoryRepository;

    MemberCommonService memberCommonService;
    ApartmentCommonService apartmentCommonService;

    /**
     * 소통공간 업로드
     * @param userToken 토큰 정보
     * @param apartmentId 현재 아파트단지 ID
     * @param dto 소통공간 정보
     */
    @Override
    public ResponseEntity<HttpStatus> uploadCommunication(JWTMemberInfoDTO userToken, Long apartmentId, CommunicationDTO.CommunicationPostReqDTO dto) {
        UserAndAPT userAndAPT = getUserAndAPT(userToken,apartmentId);
        CommunicationCategory communicationCategory = communicationCategoryRepository.findById(dto.communicationCategoryId()).get();
        Communication communication = Communication.from(userAndAPT.member,communicationCategory,dto);
        //Todo 예외처리
        communicationRepository.save(communication);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 소통공간 수정
     * @param userToken 토큰 정보
     * @param communicationId 수정하려는 게시글
     * @param dto 수정 내용
     */
    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateCommunication(JWTMemberInfoDTO userToken, Long communicationId, CommunicationDTO.CommunicationPostReqDTO dto) {
        Communication communication = communicationRepository.findById(communicationId).orElseThrow(NoSuchElementException::new);
        checkApartmentByAnnouncement(userToken,communication);
        CommunicationCategory communicationCategory = communicationCategoryRepository.findById(dto.communicationCategoryId()).get();
        //Todo 권한 확인
        //Todo 예외처리
        communication.updateCommunication(communicationCategory,dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 소통공간 삭제
     * @param userToken 토큰 정보
     * @param communicationId 삭제하려는 게시글
     */
    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteCommunication(JWTMemberInfoDTO userToken, Long communicationId) {
        Communication communication = communicationRepository.findById(communicationId).orElseThrow(NoSuchElementException::new);
        checkApartmentByAnnouncement(userToken,communication);
        //Todo 권한 확인
        //Todo 예외처리
        communication.deleteCommunication();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //작동 안됨
    @Override
    public ResponseEntity<PageResponseDTO> searchCommunication(CommunicationDTO.CommunicationSearchReqDTO reqDTO) {
        PageRequest pageable = PageRequest.of(reqDTO.getPageNumber()-1,reqDTO.getPageSize());
        reqDTO.setPageable(pageable);
        //Page<Communication> result = communicationRepository.
        return null;
        /*
         PageRequest pageable = PageRequest.of(reqDTO.getPageNumber()-1, reqDTO.getPageSize());
        reqDTO.setPageable(pageable);
        Page<Announcement> result = announcementRepository.searchAnnouncement(reqDTO);
        if (result.getContent().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        PageResponseDTO resp = new PageResponseDTO(result);
        resp.setContent(
                result
                        .getContent()
                        .stream()
                        .map(AnnouncementDTO.AnnouncementListRespDTO::new)
                        .toList()
        );
        return new ResponseEntity<>(resp, HttpStatus.OK);
         */
    }

    @Override
    public ResponseEntity<CommunicationDTO.CommunicationRespDTO> getCommunication(Long communicationtId, JWTMemberInfoDTO token) {
        return null;
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

    private void checkApartmentByAnnouncement(JWTMemberInfoDTO userToken,Communication communication){
        if (!Objects.equals(userToken.getApartmentId(), communication.getCommunicationCategoryId().getApartmentId().getApartmentId())){
            throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
        }
    }

}
