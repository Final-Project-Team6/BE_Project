package com.fastcampus.aptner.post.opinion.dto;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.dto.response.PostMemberResponse;
import com.fastcampus.aptner.post.common.enumType.BoardType;
import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.opinion.domain.Comment;
import com.fastcampus.aptner.post.opinion.domain.CommentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CommentDTO {
    @Schema(name = "댓글 생성", description = "댓글 생성 요청 Body")
    public record UploadCommentReqDTO(
            @Schema(description = "댓글 타입 => ANNOUNCEMENT(공지사항 댓글), COMPLAINT(민원 댓글), COMMUNICATION(소통 공간 댓글), REPLY(대댓글)")
            CommentType commentType,
            @Schema(description = "댓글 내용")
            String contents) {
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ViewComments {
        private Long commentId;
        private String contents;
        private PostMemberResponse commentWriter;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime createdAt;
        private PostStatus status;
        private int agree;
        private int disagree;
        private Boolean yourVote;
        private List<CommentDTO.ViewComments> children;


        public static CommentDTO.ViewComments of(Comment comment, JWTMemberInfoDTO request) {
            String tempContents = comment.getContents();
            if (comment.getStatus() != PostStatus.PUBLISHED) {
                tempContents = "삭제된 댓글 입니다.";
            }
            VoteDTO.VoteRespDTO voteRespDTO = comment.aboutVote(request);
            return new CommentDTO.ViewComments(
                    comment.getCommentId(),
                    tempContents,
                    new PostMemberResponse(comment.getMemberId()),
                    comment.getCreatedAt(),
                    comment.getStatus(),
                    voteRespDTO.agree(),
                    voteRespDTO.disagree(),
                    voteRespDTO.yourVote(),
                    comment.getChildren().stream().map(e -> CommentDTO.ViewComments.of(e, request)).collect(Collectors.toList())
            );
        }
    }

    @Schema(name = "댓글 수정", description = "댓글 수정 요청 Body")
    public record UpdateComment(String contents) {
    }

    @Getter
    public static class MyCommentResp {
        private final Long commentId;
        private final String contents;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private final LocalDateTime createdAt;
        private final BoardType boardType;
        private final Long postId;
        private final String postTitle;

        public MyCommentResp(Comment comment) {
            Post post = comment.getPost();
            this.commentId = comment.getCommentId();
            this.contents = comment.getContents();
            this.createdAt = comment.getCreatedAt();
            this.boardType = post.boardType;
            this.postId = post.postId;
            this.postTitle = post.postTitle;
        }
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class Post {
        private Long postId;
        private String postTitle;
        private BoardType boardType;
    }
}
