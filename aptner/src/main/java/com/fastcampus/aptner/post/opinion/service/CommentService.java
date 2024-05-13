package com.fastcampus.aptner.post.opinion.service;

import com.fastcampus.aptner.post.common.enumType.BoardType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<?> getCommentsResp(Long postId, BoardType boardType, MemberTempDTO.MemberAuthDTO token);
    ResponseEntity<HttpStatus> uploadComment(MemberTempDTO.MemberAuthDTO token,Long postId, CommentDTO.UploadCommentReqDTO dto);
}
