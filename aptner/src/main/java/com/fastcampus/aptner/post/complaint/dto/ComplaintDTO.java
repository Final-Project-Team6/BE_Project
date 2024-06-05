package com.fastcampus.aptner.post.complaint.dto;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.member.dto.response.PostMemberResponse;
import com.fastcampus.aptner.post.common.enumType.OrderBy;
import com.fastcampus.aptner.post.common.enumType.OrderType;
import com.fastcampus.aptner.post.common.enumType.SearchType;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.complaint.domain.ComplaintCategory;
import com.fastcampus.aptner.post.complaint.domain.ComplaintStatus;
import com.fastcampus.aptner.post.complaint.domain.ComplaintType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.opinion.dto.VoteDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ComplaintDTO {

    @Schema(name = "민원 카테고리 생성", description = "민원 카테고리 생성 요청 Body")
    public record ComplaintCategoryReqDTO(
            @Schema(description = "민원 카테고리 이름")
            String name,
            @Schema(description = "민원 카테고리 타입 => MANAGEMENT_OFFICE(관리사무소),\n" +
                    "    RESIDENTS_COMMITTEE(입주자 대표회의)")
            ComplaintType type) {
    }

    @Schema(name = "민원 카테고리 응답", description = "민원 카테고리 응답값")
    public record ComplaintCategoryRespDTO(
            Long complaintCategory,
            String name,
            ComplaintType type) {
        public ComplaintCategoryRespDTO(ComplaintCategory complaintCategory) {
            this(complaintCategory.getComplaintCategoryId(), complaintCategory.getName(), complaintCategory.getType());
        }
    }

    @Schema(name = "민원 생성", description = "민원 생성 요청 Body")
    public record ComplaintReqDTO(
            @Schema(description = "민원 카테고리 ID")
            Long complaintCategoryId,
            @Schema(description = "민원 제목")
            String title,
            @Schema(description = "민원 내용")
            String contents,
            @Schema(description = "비밀글 여부")
            boolean secret) {
    }

    @AllArgsConstructor
    @Getter
    public static class ComplaintRespDTO {
        private Long complaintId;
        private ComplaintCategoryRespDTO complaintCategoryRespDTO;
        private ComplaintStatus complaintStatus;
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
        private boolean secret;

        public ComplaintRespDTO(Complaint complaint, JWTMemberInfoDTO request, List<CommentDTO.ViewComments> comments) {
            VoteDTO.VoteRespDTO voteRespDTO = complaint.aboutVote(request);
            this.complaintId = complaint.getComplaintId();
            this.complaintCategoryRespDTO = new ComplaintDTO.ComplaintCategoryRespDTO(complaint.getComplaintCategoryId());
            this.complaintStatus = complaint.getComplaintStatus();
            this.writer = new PostMemberResponse(complaint.getMemberId());
            this.title = complaint.getTitle();
            this.createdAt = complaint.getCreatedAt();
            this.view = complaint.getView();
            this.commentCnt = complaint.getAllCommentCnt();
            this.agreeCnt = voteRespDTO.agree();
            this.disagreeCnt = voteRespDTO.disagree();
            this.yourVote = voteRespDTO.yourVote();
            this.comments = comments;
            this.secret = complaint.isSecret();
        }
    }

    @AllArgsConstructor
    @Getter
    public static class ComplaintListRespDTO {
        private Long complaintId;
        private ComplaintCategoryRespDTO complaintCategoryRespDTO;
        private ComplaintStatus complaintStatus;
        private PostMemberResponse writer;
        private String title;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime createdAt;
        private Long view;
        private int commentCnt;
        private int agreeCnt;
        private boolean secret;

        public ComplaintListRespDTO(Complaint complaint) {
            VoteDTO.VoteRespDTO voteRespDTO = complaint.aboutVoteWithoutMember();
            this.complaintId = complaint.getComplaintId();
            this.complaintCategoryRespDTO = new ComplaintDTO.ComplaintCategoryRespDTO(complaint.getComplaintCategoryId());
            this.complaintStatus = complaint.getComplaintStatus();
            this.writer = new PostMemberResponse(complaint.getMemberId());
            this.title = complaint.getTitle();
            this.createdAt = complaint.getCreatedAt();
            this.view = complaint.getView();
            this.commentCnt = complaint.getAllCommentCnt();
            this.agreeCnt = voteRespDTO.agree();
            this.secret = complaint.isSecret();
        }
    }

    @Builder
    @Getter
    @Setter
    public static class ComplaintSearchReqDTO {
        private int pageNumber;
        private int pageSize;
        private Pageable pageable;
        private Long apartmentId;
        private SearchType searchType;
        private OrderType orderType;
        private OrderBy orderBy;
        private String keyword;
        private ComplaintType complaintType;
        private Long categoryId;
        private Boolean myComplaint;
        private LocalDate period;
    }
}
