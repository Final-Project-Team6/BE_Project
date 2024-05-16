package com.fastcampus.aptner.post.announcement.dto;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementCategory;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementType;
import com.fastcampus.aptner.post.common.enumType.OrderBy;
import com.fastcampus.aptner.post.common.enumType.OrderType;
import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.common.enumType.SearchType;
import com.fastcampus.aptner.post.opinion.dto.CommentDTO;
import com.fastcampus.aptner.post.opinion.dto.VoteDTO;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;


public class AnnouncementDTO {

    @Schema(name = "공지사항 생성",description = "공지사항 생성 요청 Body")
    public record AnnouncementPostReqDTO(
            @Schema(description = "공지사항 카테고리 ID")
            Long announcementCategoryId,
            @Schema(description = "공지사항 제목")
            String title,
            @Schema(description = "공지사항 내용")
            String contents,
            @Schema(description = "공지사항 중요도.기본 0, 높을수록 우선 노출")
            Integer important,
            @Schema(description = "게시글 상태")
            PostStatus status) {
    }
    @Schema(name = "공지사항 카테고리 생성",description = "공지사항 카테고리 생성 요청 Body")
    public record AnnouncementCategoryReqDTO(
            @Schema(description = "공지사항 카테고리 이름")
            String name,
            @Schema(description = "공지사항 카테고리 타입")
            AnnouncementType type) {
    }

    @Schema(name = "공지사항 카테고리 응답",description = "공지사항 카테고리 응답값")
    public record AnnouncementCategoryRespDTO(
            @Schema(description = "공지사항 카테고리 ID")
            Long announcementCategoryId,
            @Schema(description = "공지사항 카테고리 이름")
            String name,
            @Schema(description = "공지사항 카테고리 타입")
            AnnouncementType type) {
        public AnnouncementCategoryRespDTO(AnnouncementCategory announcementCategory) {
            this(announcementCategory.getAnnouncementCategoryId(), announcementCategory.getName(), announcementCategory.getType());
        }
    }
    @Getter
    public static class AnnouncementListRespDTO {
        private Long announcementId;
        private AnnouncementCategoryRespDTO announcementCategory;
        private MemberTempDTO.MemberRespDTO writer;
        private String title;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime createdAt;
        private Long view;
        private int commentCnt;
        private int agreeCnt;
        private int disagreeCnt;
        public AnnouncementListRespDTO(Announcement announcement){
            VoteDTO.VoteRespDTO voteRespDTO = announcement.aboutVoteWithoutMember();
            this.announcementId = announcement.getAnnouncementId();
            this.announcementCategory = new AnnouncementCategoryRespDTO(announcement.getAnnouncementCategoryId());
            this.writer =new MemberTempDTO.MemberRespDTO(announcement.getMemberId());
            this.title = announcement.getTitle();
            this.createdAt = announcement.getCreatedAt();
            this.view = announcement.getView();
            this.commentCnt = announcement.getCommentList().size();
            this.agreeCnt = voteRespDTO.agree();
            this.disagreeCnt = voteRespDTO.disagree();
        }
    }
    @AllArgsConstructor
    @Getter
    @Setter
    public static class AnnouncementRespDTO {
        private Long announcementId;
        private AnnouncementCategoryRespDTO announcementCategory;
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
        public AnnouncementRespDTO(Announcement announcement, MemberTempDTO.MemberAuthDTO token){
            VoteDTO.VoteRespDTO voteRespDTO = announcement.aboutVote(token);
            this.announcementId = announcement.getAnnouncementId();
            this.announcementCategory = new AnnouncementCategoryRespDTO(announcement.getAnnouncementCategoryId());
            this.writer =new MemberTempDTO.MemberRespDTO(announcement.getMemberId());
            this.title = announcement.getTitle();
            this.createdAt = announcement.getCreatedAt();
            this.view = announcement.getView();
            this.commentCnt = announcement.getCommentList().size();
            this.agreeCnt = voteRespDTO.agree();
            this.disagreeCnt = voteRespDTO.disagree();
            this.yourVote = voteRespDTO.yourVote();
        }
    }
    @Builder
    @Getter
    @Setter
    public static class AnnouncementSearchReqDTO{
        private int pageNumber;
        private int pageSize;
        private Pageable pageable;
        private Long apartmentId;
        private SearchType searchType;
        private OrderType orderType;
        private OrderBy orderBy;
        private String keyword;
        private AnnouncementType announcementType;
        private Long categoryId;
    }
}
