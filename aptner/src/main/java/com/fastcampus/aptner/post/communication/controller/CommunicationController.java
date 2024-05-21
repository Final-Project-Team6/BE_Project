package com.fastcampus.aptner.post.communication.controller;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.domain.RoleName;
import com.fastcampus.aptner.post.common.enumType.OrderBy;
import com.fastcampus.aptner.post.common.enumType.OrderType;
import com.fastcampus.aptner.post.common.enumType.SearchType;
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
@Tag(name = "소통공간", description = "소통글 생성, 소통글 수정, 소통글 삭제, 소통글 목록 조회, 소통글 조회")
public class CommunicationController {
    private final CommunicationService communicationService;

    //TODO Member 개발 완료시 지우기

    @Operation(
            summary = "소통글 생성 API",
            description = "Schema -> 소통글 생성 \n\n apartmentId : 현재 사용중인 아파트 ID "
    )
    @PostMapping(value = "/{apartmentId}")
    public ResponseEntity<HttpStatus> uploadCommunication(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long apartmentId,
            @RequestBody CommunicationDTO.CommunicationPostReqDTO dto){
        return communicationService.uploadCommunication(memberToken, apartmentId, dto);
    }

    @Operation(
            summary = "소통글 수정 API",
            description = "Schema -> 소통글 생성\n\n apartmentId : 현재 사용중인 아파트 ID\n\n communicationId : 수정하려는 소통글 ID"
    )
    @PatchMapping(value = "/{communicationId}")
    public ResponseEntity<HttpStatus> updateCommunication(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long communicationId,
            @RequestBody CommunicationDTO.CommunicationPostReqDTO dto){
        return communicationService.updateCommunication(memberToken, communicationId, dto);
    }

    @Operation(
            summary = "소통글 삭제 API",
            description = "apartmentId : 현재 사용중인 아파트 ID\n\n communicationId : 삭제하려는 소통글 ID"
    )
    @DeleteMapping(value = "/{communicationId}")
    public ResponseEntity<HttpStatus> deleteCommunication(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long communicationId){
        return communicationService.deleteCommunication(memberToken, communicationId);
    }


    @GetMapping("/search/{apartmentId}")
    public ResponseEntity<?> searchCommunication(
            @PathVariable Long apartmentId,
            @RequestParam(required = false, defaultValue = "1") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "TITLE") SearchType searchType,
            @RequestParam(required = false, defaultValue = "DATE") OrderType orderType,
            @RequestParam(required = false, defaultValue = "DESC") OrderBy orderBy,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId){
        CommunicationDTO.CommunicationSearchReqDTO reqDTO = CommunicationDTO.CommunicationSearchReqDTO.builder()
                .apartmentId(apartmentId)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .searchType(searchType)
                .orderType(orderType)
                .orderBy(orderBy)
                .keyword(keyword)
                .categoryId(categoryId)
                .build();
        return null;
    }
}
