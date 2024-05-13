package com.fastcampus.aptner.post.opinion.repository;

import com.fastcampus.aptner.post.common.enumType.BoardType;
import com.fastcampus.aptner.post.opinion.domain.Comment;

import java.util.List;

public interface CommentRepositoryDsl {
    List<Comment> getComments(Long postId, BoardType boardType);
}
