package com.fastcampus.aptner.post.announcement.controller;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.jwt.util.isLogin;
import com.fastcampus.aptner.member.domain.RoleName;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementType;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fastcampus.aptner.post.announcement.service.AnnouncementService;
import com.fastcampus.aptner.post.common.dto.PageResponseDTO;
import com.fastcampus.aptner.post.common.enumType.OrderBy;
import com.fastcampus.aptner.post.common.enumType.OrderType;
import com.fastcampus.aptner.post.common.enumType.SearchType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/announcement/")
@Tag(name = "공지사항(사용자)", description = "공지사항 목록 조회, 공지사항 조회")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    //todo 날짜 조건 걸기
    @Operation(
            summary = "공지사항 목록 조회 API",
            description = "apartmentId : 현재 사용중인 아파트 ID \n\n" +
                    "pageNumber : 조회 페이지 번호\n\n" +
                    "pageSize : 페이지당 내용 개수\n\n" +
                    "searchType : 검색어 검색 조건 => TITLE(제목), CONTENTS(내용), TITLE_CONTENTS(제목+내용);\n\n" +
                    "orderType : 정렬 조건 => VIEW(조회수), COMMENT(댓글), VOTE(공감수), DATE(날짜)\n\n" +
                    "orderBy : 정렬 차순 => ASC(오름차순), DESC(내림차순)\n\n" +
                    "keyword : 검색어\n\n" +
                    "announcementType : 공지사항 타입 =>  NOTICE(공지사항), DISCLOSURE(의무공개 사항)\n\n" +
                    "categoryId : 공지사항 카테고리 ID\n\n" +
                    "important : 중요글 여부 => true 중요글만, false 중요글이 아닌 글만, null 조건X \n\n"+
                    "apartmentId 를 제외한 나머지 값은 필수가 아니며, 포함하지 않으면 기본조건으로 처리하거나 영향을 주지 않습니다."
    )
    @GetMapping("/search/{apartmentId}")
    public ResponseEntity<?> searchAnnouncement(
            @PathVariable Long apartmentId,
            @RequestParam(required = false, defaultValue = "1") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "TITLE") SearchType searchType,
            @RequestParam(required = false, defaultValue = "DATE") OrderType orderType,
            @RequestParam(required = false, defaultValue = "DESC") OrderBy orderBy,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) AnnouncementType announcementType,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean important){
        AnnouncementDTO.AnnouncementSearchReqDTO reqDTO = AnnouncementDTO.AnnouncementSearchReqDTO.builder()
                .apartmentId(apartmentId)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .searchType(searchType)
                .orderType(orderType)
                .orderBy(orderBy)
                .keyword(keyword)
                .announcementType(announcementType)
                .categoryId(categoryId)
                .important(important)
                .build();
        return announcementService.searchAnnouncement(reqDTO);
    }

    // todo 날짜 조건 걸기, Status 반영해서 가져오기
    @Operation(
            summary = "공지사항 조회 API",
            description = "announcementId : 조회하려는 공지사항 ID"
    )
    @GetMapping("/{announcementId}")
    public ResponseEntity<?> getAnnouncement(
            @AuthenticationPrincipal JWTMemberInfoDTO request,
            @PathVariable Long announcementId){
        System.out.println("=====================\n\n"+ request.getMemberId()+ request.getApartmentName());
        return announcementService.getAnnouncement(announcementId,request);
    }
}
