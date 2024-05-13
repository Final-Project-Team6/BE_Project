package com.fastcampus.aptner.post.opinion.service;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.announcement.service.AnnouncementCommonService;
import com.fastcampus.aptner.post.common.enumType.BoardType;
import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.opinion.domain.Comment;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.opinion.repository.CommentRepository;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import com.fastcampus.aptner.post.temp.service.MemberCommonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    private final CommentCommonService commentCommonService;
    private final MemberCommonService memberCommonService;
    private final AnnouncementCommonService announcementCommonService;


    @Override
    public ResponseEntity<?> getCommentsResp(Long postId,BoardType boardType, MemberTempDTO.MemberAuthDTO token) {
        List<CommentDTO.ViewComments> list = commentCommonService.getComments(postId,boardType,token);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> uploadComment(MemberTempDTO.MemberAuthDTO token, Long postId, CommentDTO.UploadCommentReqDTO dto){
        Member member = memberCommonService.getUserByToken(token);
        Comment comment = Comment.builder()
                .memberId(member)
                .contents(dto.contents())
                .status(PostStatus.PUBLISHED)
                .build();
        switch (dto.commentType()){
            case ANNOUNCEMENT -> {
                Announcement announcement = announcementCommonService.getAnnouncementEntity(postId);
                comment.setAnnouncementId(announcement);
                commentRepository.save(comment);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            case REPLY -> {
                Comment parent = commentCommonService.getCommentEntity(postId);
                comment.setParentId(parent);
                commentRepository.save(comment);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            //todo 민원, 소통 댓글 구현
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
