package com.fastcampus.aptner.post.communication.service.admin;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface CommunicationAdminService {

    ResponseEntity<HttpStatus> deleteCommunicationAdmin(JWTMemberInfoDTO userToken, Long communicationId);
}
