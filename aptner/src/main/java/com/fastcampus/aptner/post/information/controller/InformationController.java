package com.fastcampus.aptner.post.information.controller;

import com.fastcampus.aptner.member.domain.RoleName;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import com.fastcampus.aptner.post.information.service.InformationService;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/information")
@Tag(name = "정보공간", description = "정보글 생성, 정보글 수정, 정보글 삭제, 정보글 목록 조회, 정보글 조회")
public class InformationController {
    private final InformationService informationService;

    //TODO Member 개발 완료시 지우기
    private MemberTempDTO.MemberAuthDTO memberTempToken = new MemberTempDTO.MemberAuthDTO(1L, RoleName.ADMIN,1L);


    @Operation(
            summary = "정보글 생성 API",
            description = "Schema -> 정보글 생성 \n\n apartmentId : 현재 사용중인 아파트 ID "
    )
    @PostMapping(value = "/{apartmentId}")
    public ResponseEntity<HttpStatus> uploadInformation(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long apartmentId,
            @RequestBody InformationDTO.InformationPostReqDTO dto){
        return informationService.uploadInformation(memberTempToken, apartmentId, dto);
    }

    @Operation(
            summary = "정보글 수정 API",
            description = "Schema -> 정보글 생성\n\n apartmentId : 현재 사용중인 아파트 ID\n\n informationId : 수정하려는 정보글 ID"
    )
    @PatchMapping(value = "/{informationId}")
    public ResponseEntity<HttpStatus> updateInformation(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long informationId,
            @RequestBody InformationDTO.InformationPostReqDTO dto){
        return informationService.updateInformation(memberTempToken, informationId, dto);
    }

    @Operation(
            summary = "정보글 삭제 API",
            description = "apartmentId : 현재 사용중인 아파트 ID\n\n informationId : 삭제하려는 정보글 ID"
    )
    @DeleteMapping(value = "/{informationId}")
    public ResponseEntity<HttpStatus> deleteCommunication(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO meberToken,
            @PathVariable Long informationId){
        return informationService.deleteInformation(memberTempToken, informationId);
    }

}
