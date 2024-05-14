package com.fastcampus.aptner.post.opinion.service;

import com.fastcampus.aptner.post.common.enumType.BoardType;
import com.fastcampus.aptner.post.opinion.domain.CommentType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    ResponseEntity<List<CommentDTO.ViewComments>> getCommentsResp(Long postId, CommentType commentType, MemberTempDTO.MemberAuthDTO token);
    ResponseEntity<HttpStatus> uploadComment(MemberTempDTO.MemberAuthDTO token,Long postId, CommentDTO.UploadCommentReqDTO dto);
    ResponseEntity<HttpStatus> updateComment(MemberTempDTO.MemberAuthDTO token, Long commentId, String contents);
    ResponseEntity<HttpStatus> deleteComment(MemberTempDTO.MemberAuthDTO token, Long commentId);

}
