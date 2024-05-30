package com.fastcampus.aptner.post.communication.service;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.global.handler.exception.CustomDataNotFoundException;
import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.common.dto.PageResponseDTO;
import com.fastcampus.aptner.post.common.error.PostErrorCode;
import com.fastcampus.aptner.post.communication.domain.Communication;
import com.fastcampus.aptner.post.communication.domain.CommunicationCategory;
import com.fastcampus.aptner.post.communication.dto.CommunicationDTO;
import com.fastcampus.aptner.post.communication.repository.CommunicationCategoryRepository;
import com.fastcampus.aptner.post.communication.repository.CommunicationRepository;
import com.fastcampus.aptner.apartment.service.ApartmentCommonService;
import com.fastcampus.aptner.member.service.MemberCommonService;
import com.fastcampus.aptner.post.opinion.domain.CommentType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.opinion.service.CommentCommonService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.fastcampus.aptner.global.error.CommonErrorCode.MUST_AUTHORIZE;
import static com.fastcampus.aptner.post.common.error.PostErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommunicationServiceImpl implements CommunicationService {

    CommunicationRepository communicationRepository;
    CommunicationCategoryRepository communicationCategoryRepository;

    MemberCommonService memberCommonService;
    ApartmentCommonService apartmentCommonService;
    CommentCommonService commentCommonService;

    @Override
    public ResponseEntity<HttpStatus> uploadCommunication(JWTMemberInfoDTO userToken, Long apartmentId, CommunicationDTO.CommunicationPostReqDTO dto) {
        if(userToken==null) throw new RestAPIException(MUST_AUTHORIZE);
        UserAndAPT userAndAPT = getUserAndAPT(userToken,apartmentId);
        CommunicationCategory communicationCategory = communicationCategoryRepository.findById(dto.communicationCategoryId()).orElseThrow(()->new RestAPIException(NO_SUCH_CATEGORY));
        communicationRepository.save(Communication.from(userAndAPT.member,communicationCategory,dto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> updateCommunication(JWTMemberInfoDTO userToken, Long communicationId, CommunicationDTO.CommunicationPostReqDTO dto) {
        Communication communication = communicationRepository.findById(communicationId).orElseThrow(()->new RestAPIException(NO_SUCH_POST));
        checkMemberByCommunication(userToken,communication);
        CommunicationCategory communicationCategory = communicationCategoryRepository.findById(dto.communicationCategoryId()).orElseThrow(()->new RestAPIException(NO_SUCH_CATEGORY));
        if(!Objects.equals(communication.getCommunicationCategoryId().getCommunicationCategoryId(), communicationCategory.getCommunicationCategoryId()))
            throw new CustomDataNotFoundException("카테고리가 동일하지 않습니다.");
        communication.updateCommunication(communicationCategory,dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> deleteCommunication(JWTMemberInfoDTO userToken, Long communicationId) {
        Communication communication = communicationRepository.findById(communicationId).orElseThrow(()->new RestAPIException(NO_SUCH_POST));
        requestHasRole(userToken,communication);
        communication.deleteCommunication();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<PageResponseDTO> searchCommunication(CommunicationDTO.CommunicationSearchReqDTO reqDTO,JWTMemberInfoDTO memberToken) {
        PageRequest pageable = PageRequest.of(reqDTO.getPageNumber()-1,reqDTO.getPageSize());
        reqDTO.setPageable(pageable);
        Page<Communication> result = communicationRepository.searchCommunication(reqDTO,memberToken);
        if (result.getContent().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        PageResponseDTO resp = new PageResponseDTO(result);
        resp.setContent(
                result
                        .getContent()
                        .stream()
                        .map(CommunicationDTO.CommunicationListRespDTO::new)
                        .toList()
        );
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CommunicationDTO.CommunicationRespDTO> getCommunication(Long communicationId, JWTMemberInfoDTO token) {
        Communication communication = communicationRepository.findById(communicationId).orElseThrow(()->new RestAPIException(NO_SUCH_POST));
        if (communication.isSecret()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        CommunicationDTO.CommunicationRespDTO resp = new CommunicationDTO.CommunicationRespDTO(communication,token);
        List<CommentDTO.ViewComments> comments = commentCommonService.getComments(communicationId, CommentType.COMMUNICATION,token);
        resp.setComments(comments);
        communication.addViewCount();
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    @AllArgsConstructor
    public static class UserAndAPT{
        Member member;
        Apartment apartment;
    }

    private UserAndAPT getUserAndAPT(JWTMemberInfoDTO userToken, Long apartmentId){
        if (!Objects.equals(userToken.getApartmentId(), apartmentId)){
            throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
        }
        Member member = memberCommonService.getUserByToken(userToken);
        Apartment apartment = apartmentCommonService.getApartmentById(apartmentId);
        return new UserAndAPT(member, apartment);
    }

    private void checkMemberByCommunication(JWTMemberInfoDTO userToken,Communication communication){
        if (userToken==null) {
            throw new RestAPIException(MUST_AUTHORIZE);
        }
        if (!Objects.equals(userToken.getMemberId(), communication.getMemberId().getMemberId())){
            throw new RestAPIException(NOT_SAME_USER);
        }
    }

    private static void requestHasRole(JWTMemberInfoDTO userToken, Communication communication) {
        if (userToken == null) {
            throw new RestAPIException(MUST_AUTHORIZE);
        }
        if (userToken.getMemberId() != communication.getMemberId().getMemberId()) {
            if (userToken.getRoleName().equals("USER")) {
                throw new RestAPIException(NOT_SAME_USER);
            }
            if (userToken.getApartmentId() != communication.getCommunicationCategoryId().getApartmentId().getApartmentId()) {
                throw new RestAPIException(PostErrorCode.NOT_ALLOWED_APARTMENT);
            }
        }
    }

}
