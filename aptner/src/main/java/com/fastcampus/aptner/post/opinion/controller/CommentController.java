package com.fastcampus.aptner.post.opinion.controller;

import com.fastcampus.aptner.member.domain.RoleName;
import com.fastcampus.aptner.post.common.enumType.BoardType;
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
                    "boardType : 조회하려는 게시글 타입 (공지사항, 민원 게시판, 소통 공간, 아파트 정보X) "
    )
    @GetMapping("/{postId}")
    public ResponseEntity<?> getComments(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long postId,
            @RequestParam BoardType boardType){
        return commentService.getCommentsResp(postId,boardType,memberTempToken);
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
        return commentService.uploadComment(memberToken,postId,dto);
    }
}
