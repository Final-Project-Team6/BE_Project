package com.fastcampus.aptner.post.opinion.service;

import com.fastcampus.aptner.post.opinion.domain.Comment;
import com.fastcampus.aptner.post.opinion.domain.CommentType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.opinion.repository.CommentRepository;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentCommonServiceImpl implements CommentCommonService {

    private final CommentRepository commentRepository;

    @Override
    public List<CommentDTO.ViewComments> getComments(Long postId, CommentType commentType, MemberTempDTO.MemberAuthDTO token) {
        return commentRepository
                .getComments(postId, commentType)
                .stream()
                .map(e -> CommentDTO.ViewComments.of(e, token))
                .toList();
    }

    @Override
    public Comment getCommentEntity(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(NoSuchElementException::new);
    }
}
