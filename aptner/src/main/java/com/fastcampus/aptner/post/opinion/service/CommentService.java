package com.fastcampus.aptner.post.opinion.service;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.opinion.domain.CommentType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    ResponseEntity<List<CommentDTO.ViewComments>> getCommentsResp(Long postId, CommentType commentType, JWTMemberInfoDTO request);

    ResponseEntity<HttpStatus> uploadComment(JWTMemberInfoDTO token, Long postId, CommentDTO.UploadCommentReqDTO dto);

    ResponseEntity<HttpStatus> updateComment(JWTMemberInfoDTO token, Long commentId, String contents);

    ResponseEntity<HttpStatus> deleteComment(JWTMemberInfoDTO token, Long commentId);

}
