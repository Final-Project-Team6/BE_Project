package com.fastcampus.aptner.post.opinion.repository;

import com.fastcampus.aptner.post.opinion.domain.Comment;
import com.fastcampus.aptner.post.opinion.domain.CommentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepositoryDsl {
    List<Comment> getComments(Long postId, CommentType commentType);

    Page<Comment> getMyComments(Long memberId, Pageable pageable,CommentType commentType);
}
