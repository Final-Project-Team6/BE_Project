package com.fastcampus.aptner.post.complaint.dto;

import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementType;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.complaint.domain.ComplaintCategory;
import com.fastcampus.aptner.post.complaint.domain.ComplaintStatus;
import com.fastcampus.aptner.post.complaint.domain.ComplaintType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.opinion.dto.VoteDTO;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

public class ComplaintDTO {

    @Schema(name = "민원 카테고리 생성",description = "민원 카테고리 생성 요청 Body")
    public record ComplaintCategoryReqDTO(
            @Schema(description = "민원 카테고리 이름")
            String name,
            @Schema(description = "민원 카테고리 타입")
            ComplaintType type) {
    }
    @Schema(name = "민원 카테고리 응답",description = "민원 카테고리 응답값")
    public record ComplaintCategoryRespDTO(
            Long complaintCategory,
            String name,
            ComplaintType type){
        public ComplaintCategoryRespDTO(ComplaintCategory complaintCategory){
            this(complaintCategory.getComplaintCategoryId(),complaintCategory.getName(),complaintCategory.getType());
        }
    }
    @Schema(name = "민원 생성",description = "민원 생성 요청 Body")
    public record ComplaintReqDTO(
            @Schema(description = "민원 카테고리 ID")
            Long complaintCategoryId,
            @Schema(description = "민원 제목")
            String title,
            @Schema(description = "민원 내용")
            String contents,
            @Schema(description = "비밀글 여부")
            boolean secret){}

    @AllArgsConstructor
    @Getter
    public static class ComplaintRespDTO {
        private Long complaintId;
        private ComplaintCategoryRespDTO complaintCategoryRespDTO;
        private ComplaintStatus complaintStatus;
        private MemberTempDTO.MemberRespDTO writer;
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

        public ComplaintRespDTO(Complaint complaint, MemberTempDTO.MemberAuthDTO token,List<CommentDTO.ViewComments> comments) {
            VoteDTO.VoteRespDTO voteRespDTO = complaint.aboutVote(token);
            this.complaintId = complaint.getComplaintId();
            this.complaintCategoryRespDTO = new ComplaintDTO.ComplaintCategoryRespDTO(complaint.getComplaintCategoryId());
            this.complaintStatus =complaint.getComplaintStatus();
            this.writer = new MemberTempDTO.MemberRespDTO(complaint.getMemberId());
            this.title = complaint.getTitle();
            this.createdAt = complaint.getCreatedAt();
            this.view = complaint.getView();
            this.commentCnt = complaint.getCommentList().size();
            this.agreeCnt = voteRespDTO.agree();
            this.disagreeCnt = voteRespDTO.disagree();
            this.yourVote = voteRespDTO.yourVote();
            this.comments = comments;
        }
    }
}
