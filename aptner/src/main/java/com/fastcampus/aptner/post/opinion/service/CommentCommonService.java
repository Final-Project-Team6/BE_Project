package com.fastcampus.aptner.post.opinion.service;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.common.enumType.BoardType;
import com.fastcampus.aptner.post.opinion.domain.Comment;
import com.fastcampus.aptner.post.opinion.domain.CommentType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;

import java.util.List;

public interface CommentCommonService {
    List<CommentDTO.ViewComments> getComments(Long postId, CommentType commentType, JWTMemberInfoDTO request);

    Comment getCommentEntity(Long commentId);
}
