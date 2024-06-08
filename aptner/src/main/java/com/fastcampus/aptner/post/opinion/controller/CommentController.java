package com.fastcampus.aptner.post.opinion.controller;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.opinion.domain.CommentType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.opinion.service.CommentService;
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

    @Operation(
            summary = "댓글 조회 API",
            description = "postId : 댓글을 조회하려는 게시글 ID\n\n" +
                    "comment : 조회하려는 게시글 타입 ANNOUNCEMENT(\"공지사항 댓글\"), COMPLAINT(\"민원 댓글\"), COMMUNICATION(\"소통 공간 댓글\"), REPLY(\"대댓글에 대한 조회는 구현하지 않음. 요청X\") "
    )
    @GetMapping("/{postId}")
    public ResponseEntity<?> getComments(
            @AuthenticationPrincipal JWTMemberInfoDTO request,
            @PathVariable Long postId,
            @RequestParam CommentType commentType) {
        return commentService.getCommentsResp(postId, commentType, request);
    }

    @Operation(
            summary = "댓글 생성 API",
            description = "Schema -> 댓글 생성\n\n" +
                    "postId : 생성하려는 게시글 ID"
    )
    @PostMapping("/{postId}")
    public ResponseEntity<HttpStatus> uploadComment(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long postId,
            @RequestBody CommentDTO.UploadCommentReqDTO dto) {
        return commentService.uploadComment(memberToken, postId, dto);
    }

    @Operation(
            summary = "댓글 수정 API",
            description = "commentId : 생성하려는 게시글 ID\n\n" +
                    "contents : 댓글 내용"
    )
    @PatchMapping("/{commentId}")
    public ResponseEntity<HttpStatus> updateComment(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long commentId,
            @RequestBody CommentDTO.UpdateComment contents) {
        return commentService.updateComment(memberToken, commentId, contents.contents());
    }

    @Operation(
            summary = "댓글 삭제 API",
            description = "commentId : 생성하려는 게시글 ID"
    )
    @DeleteMapping("/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long commentId) {
        return commentService.deleteComment(memberToken, commentId);
    }

    @Operation(
            summary = "내 댓글 조회 API",
            description = "pageNumber : 조회 페이지 번호\n\n" +
                    "pageSize : 페이지당 내용 개수\n\n" +
                    "commentType : 조회하려는 게시판 타입"
    )
    @GetMapping("/")
    public ResponseEntity<?> getMyCommentList(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @RequestParam(required = false, defaultValue = "1") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false) CommentType commentType) {
        return commentService.getMyCommentList(memberToken, pageNumber, pageSize,commentType);
    }
}
