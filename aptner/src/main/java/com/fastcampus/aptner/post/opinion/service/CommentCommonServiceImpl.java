package com.fastcampus.aptner.post.opinion.service;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.opinion.domain.Comment;
import com.fastcampus.aptner.post.opinion.domain.CommentType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.opinion.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentCommonServiceImpl implements CommentCommonService {

    private final CommentRepository commentRepository;

    @Override
    public List<CommentDTO.ViewComments> getComments(Long postId, CommentType commentType, JWTMemberInfoDTO request) {
        return commentRepository
                .getComments(postId, commentType)
                .stream()
                .map(e -> CommentDTO.ViewComments.of(e, request))
                .toList();
    }

    @Override
    public Comment getCommentEntity(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(NoSuchElementException::new);
    }
}
