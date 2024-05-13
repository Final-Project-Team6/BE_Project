package com.fastcampus.aptner.post.opinion.service;

import com.fastcampus.aptner.post.common.enumType.BoardType;
import com.fastcampus.aptner.post.opinion.domain.Comment;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;

import java.util.List;

public interface CommentCommonService {
    List<CommentDTO.ViewComments> getComments(Long postId, BoardType boardType, MemberTempDTO.MemberAuthDTO token);

    Comment getCommentEntity(Long commentId);
}
