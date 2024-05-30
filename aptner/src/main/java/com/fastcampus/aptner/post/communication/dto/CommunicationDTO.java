package com.fastcampus.aptner.post.communication.dto;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.dto.response.PostMemberResponse;
import com.fastcampus.aptner.post.common.enumType.OrderBy;
import com.fastcampus.aptner.post.common.enumType.OrderType;
import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.common.enumType.SearchType;
import com.fastcampus.aptner.post.communication.domain.Communication;
import com.fastcampus.aptner.post.communication.domain.CommunicationCategory;
import com.fastcampus.aptner.post.communication.domain.CommunicationType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.opinion.dto.VoteDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public class CommunicationDTO {

    @Schema(name = "소통공간 생성",description = "소통공간 생성 요청 Body")
    public record CommunicationPostReqDTO(
            @Schema(description = "소통공간 카테고리 ID")
            Long communicationCategoryId,
            @Schema(description = "소통공간 제목")
            String title,
            @Schema(description = "소통공간 내용")
            String contents,
            @Schema(description = "게시글 상태 => PUBLISHED(게시됨), HIDDEN(숨김), DELETED(삭제됨), FORCE_DELETED(강제 삭제됨)")
            PostStatus status,
            @Schema(description = "비밀 여부 (기본값: false)")
            Boolean secret){
        public CommunicationPostReqDTO(Long communicationCategoryId, String title, String contents, PostStatus status) {
            this(communicationCategoryId, title, contents, status, false);
        }
    }
    @Schema(name = "소통공간 카테고리 생성",description = "소통공간 카테고리 생성 요청 Body")
    public record CommunicationCategoryReqDTO(
            @Schema(description = "소통공간 카테고리 이름")
            String name,
            @Schema(description = "소통공간 카테고리 타입 => USER_COMMU(입주민 소통공간),\n" +
                    "    REPRESENT_COMMU(입대의 소통공간)")
            CommunicationType type){
    }

    @Schema(name = "소통공간 카테고리 응답",description = "소통공간 카테고리 응답값")
    public record CommunicationCategoryRespDTO(
            Long communicationCategoryId,
            String name,
            CommunicationType type
            ){
        public CommunicationCategoryRespDTO(CommunicationCategory communicationCategory){
            this(communicationCategory.getCommunicationCategoryId(), communicationCategory.getName(),communicationCategory.getType());
        }
    }
    @AllArgsConstructor
    @Getter
    @ToString
    public static class CommunicationListRespDTO{
        private final Long communicationId;
        private final CommunicationCategoryRespDTO communicationCategory;
        private final PostMemberResponse writer;
        private final String title;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private final LocalDateTime createdAt;
        private final Long view;
        private final int commentCnt;
        private final int agreeCnt;
        private final int disagreeCnt;
        public CommunicationListRespDTO(Communication communication){
            VoteDTO.VoteRespDTO voteRespDTO=communication.aboutVoteWithoutMember();
            this.communicationId = communication.getCommunicationId();
            this.communicationCategory = new CommunicationCategoryRespDTO(communication.getCommunicationCategoryId());
            this.writer = new PostMemberResponse(communication.getMemberId());
            this.title = communication.getTitle();
            this.createdAt = communication.getCreatedAt();
            this.view = communication.getView();
            this.commentCnt = communication.getCommentList().size();
            this.agreeCnt = voteRespDTO.agree();
            this.disagreeCnt = voteRespDTO.disagree();
        }
    }


    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommunicationRespDTO {
        private Long communicationId;
        private CommunicationCategoryRespDTO communicationCategory;
        private PostMemberResponse writer;
        private String title;
        private String contents;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime createdAt;
        private Long view;
        private int commentCnt;
        private int agreeCnt;
        private int disagreeCnt;
        private Boolean yourVote;
        private List<CommentDTO.ViewComments> comments;
        public CommunicationRespDTO(Communication communication, JWTMemberInfoDTO token){
            VoteDTO.VoteRespDTO voteRespDTO=communication.aboutVote(token);
            this.communicationId = communication.getCommunicationId();
            this.communicationCategory = new CommunicationCategoryRespDTO(communication.getCommunicationCategoryId());
            this.writer = new PostMemberResponse(communication.getMemberId());
            this.title = communication.getTitle();
            this.contents = communication.getContents();
            this.createdAt = communication.getCreatedAt();
            this.view = communication.getView();
            this.commentCnt = communication.getCommentList().size();
            this.agreeCnt = voteRespDTO.agree();
            this.disagreeCnt = voteRespDTO.disagree();
            this.yourVote = voteRespDTO.yourVote();
        }
    }
    @Builder
    @Getter
    @Setter
    public static class CommunicationSearchReqDTO{
        private int pageNumber;
        private int pageSize;
        private Pageable pageable;
        private Long apartmentId;
        private SearchType searchType;
        private OrderType orderType;
        private OrderBy orderBy;
        private String keyword;
        private PostStatus status;
        private CommunicationType communicationType;
        private Long categoryId;
    }
}
