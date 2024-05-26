package com.fastcampus.aptner.post.communication.controller.admin;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.communication.service.admin.CommunicationAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/communication/admin")
@Tag(name = "소통공간(관리자)", description = "소통글 삭제")
public class CommunicationAdminController {

    private final CommunicationAdminService communicationAdminService;

    @Operation(
            summary = "소통글 삭제 API",
            description = "apartmentId : 현재 사용중인 아파트 ID\n\n communicationId : 삭제하려는 소통글 ID"
    )
    @DeleteMapping(value = "/{communicationId}")
    public ResponseEntity<HttpStatus> deleteCommunication(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long communicationId){
        return communicationAdminService.deleteCommunicationAdmin(memberToken, communicationId);
    }


}
