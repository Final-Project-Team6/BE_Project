package com.fastcampus.aptner.post.communication.controller;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.common.enumType.OrderBy;
import com.fastcampus.aptner.post.common.enumType.OrderType;
import com.fastcampus.aptner.post.common.enumType.SearchType;
import com.fastcampus.aptner.post.communication.domain.CommunicationType;
import com.fastcampus.aptner.post.communication.dto.CommunicationDTO;
import com.fastcampus.aptner.post.communication.service.CommunicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/communication")
@Tag(name = "소통공간(사용자)", description = "소통글 생성, 소통글 수정, 소통글 삭제, 소통글 목록 조회, 소통글 조회")
public class CommunicationController {
    private final CommunicationService communicationService;

    @Operation(
            summary = "소통공간 생성 API",
            description = "Schema -> 소통공간 생성 \n\n apartmentId : 현재 사용중인 아파트 ID "
    )
    @PostMapping(value = "/{apartmentId}")
    public ResponseEntity<HttpStatus> uploadCommunication(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long apartmentId,
            @RequestBody CommunicationDTO.CommunicationPostReqDTO dto){
        return communicationService.uploadCommunication(memberToken, apartmentId, dto);
    }

    @Operation(
            summary = "소통공간 수정 API",
            description = "Schema -> 소통공간 생성\n\n communicationId : 수정하려는 소통글 ID"
    )
    @PatchMapping(value = "/{communicationId}")
    public ResponseEntity<HttpStatus> updateCommunication(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long communicationId,
            @RequestBody CommunicationDTO.CommunicationPostReqDTO dto){
        return communicationService.updateCommunication(memberToken, communicationId, dto);
    }

    @Operation(
            summary = "소통공간 삭제 API",
            description = "communicationId : 삭제하려는 소통글 ID"
    )
    @DeleteMapping(value = "/{communicationId}")
    public ResponseEntity<HttpStatus> deleteCommunication(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long communicationId){
        return communicationService.deleteCommunication(memberToken, communicationId);
    }

    @Operation(
            summary = "소통공간 목록 조회 API",
            description = "apartmentId : 현재 사용중인 아파트 ID\n\n" +
                    "pageNumber : 조회 페이지 번호\n\n" +
                    "pageSize : 페이지당 내용 개수\n\n" +
                    "searchType : 검색어 검색 조건 => TITLE(제목), CONTENTS(내용), TITLE_CONTENTS(제목+내용), NICKNAME(닉네임)\n\n" +
                    "orderType : 정렬 조건 => VIEW(조회수), COMMENT(댓글), VOTE(공감수), DATE(날짜)\n\n" +
                    "orderBy : 정렬 차순 => ASC(오름차순), DESC(내림차순)\n\n" +
                    "keyword : 검색어\n\n" +
                    "communicationType : USER_COMMU(입주민 소통공간),REPRESENT_COMMU(입대의 소통공간)\n\n" +
                    "categoryId : 소통글 카테고리 ID\n\n" +
                    "myCommunication : 내 소통글만 보이기 => true 내 소통글만 반환, false & null 조건 처리 X\n\n" +
                    "apartmentId 를 제외한 나머지 값은 필수가 아니며, 포함하지 않으면 기본조건으로 처리하거나 영향을 주지 않습니다."
    )
    @GetMapping("/search/{apartmentId}")
    public ResponseEntity<?> searchCommunication(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long apartmentId,
            @RequestParam(required = false, defaultValue = "1") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "TITLE") SearchType searchType,
            @RequestParam(required = false, defaultValue = "DATE") OrderType orderType,
            @RequestParam(required = false, defaultValue = "DESC") OrderBy orderBy,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) CommunicationType communicationType,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean myCommunication){
        CommunicationDTO.CommunicationSearchReqDTO reqDTO = CommunicationDTO.CommunicationSearchReqDTO.builder()
                .apartmentId(apartmentId)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .searchType(searchType)
                .orderType(orderType)
                .orderBy(orderBy)
                .keyword(keyword)
                .communicationType(communicationType)
                .categoryId(categoryId)
                .myCommunication(myCommunication)
                .build();
        return communicationService.searchCommunication(reqDTO,memberToken);
    }

    @Operation(
            summary = "소통공간 조회 API",
            description = "communicationId : 조회하려는 소통글 ID"
    )
    @GetMapping("/{communicationId}")
    public ResponseEntity<?> getCommunication(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long communicationId){
        return communicationService.getCommunication(communicationId,memberToken);
    }
}
