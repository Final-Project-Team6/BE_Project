package com.fastcampus.aptner.post.opinion.controller;

import com.fastcampus.aptner.member.domain.RoleName;
import com.fastcampus.aptner.post.common.enumType.BoardType;
import com.fastcampus.aptner.post.opinion.domain.CommentType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.opinion.service.CommentService;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/comment/")
@Tag(name = "댓글(사용자)", description = "댓글 생성, 댓글 조회")
public class CommentController {
    private final CommentService commentService;

    //TODO Member 개발 완료시 지우기
    private MemberTempDTO.MemberAuthDTO memberTempToken = new MemberTempDTO.MemberAuthDTO(1L, RoleName.ADMIN,1L);
    @Operation(
            summary = "댓글 조회 API",
            description = "postId : 댓글을 조회하려는 게시글 ID\n\n" +
                    "comment : 조회하려는 게시글 타입 ANNOUNCEMENT(\"공지사항 댓글\"), COMPLAINT(\"민원 댓글\"), COMMUNICATION(\"소통 공간 댓글\"), REPLY(\"대댓글에 대한 조회는 구현하지 않음. 요청X\") "
    )
    @GetMapping("/{postId}")
    public ResponseEntity<?> getComments(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long postId,
            @RequestParam CommentType commentType){
        return commentService.getCommentsResp(postId,commentType,memberTempToken);
    }

    @Operation(
            summary = "댓글 생성 API",
            description = "Schema -> 댓글 생성\n\n" +
                    "postId : 생성하려는 게시글 ID"
    )
    @PostMapping("/{postId}")
    public ResponseEntity<HttpStatus> uploadComment(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long postId,
            @RequestBody CommentDTO.UploadCommentReqDTO dto){
        return commentService.uploadComment(memberTempToken,postId,dto);
    }

    @Operation(
            summary = "댓글 수정 API",
            description = "commentId : 생성하려는 게시글 ID\n\n" +
                    "contents : 댓글 내용"
    )
    @PatchMapping("/{commentId}")
    public ResponseEntity<HttpStatus> updateComment(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long commentId,
            @RequestBody CommentDTO.UpdateComment contents){
        return commentService.updateComment(memberTempToken,commentId,contents.contents());
    }

    @Operation(
            summary = "댓글 삭제 API",
            description = "commentId : 생성하려는 게시글 ID"
    )
    @DeleteMapping("/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long commentId){
        return commentService.deleteComment(memberTempToken,commentId);
    }
}
