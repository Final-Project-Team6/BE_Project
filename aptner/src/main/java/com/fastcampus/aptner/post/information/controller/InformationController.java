package com.fastcampus.aptner.post.information.controller;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.common.enumType.OrderBy;
import com.fastcampus.aptner.post.common.enumType.OrderType;
import com.fastcampus.aptner.post.common.enumType.SearchType;
import com.fastcampus.aptner.post.information.domain.InformationType;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import com.fastcampus.aptner.post.information.service.InformationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/information")
@Tag(name = "정보공간(사용자)", description = "정보글 목록 조회, 정보글 조회")
public class InformationController {
    private final InformationService informationService;

    @Operation(
            summary = "공지사항 목록 조회 API",
            description = "apartmentId : 현재 사용중인 아파트 ID \n\n" +
                    "pageNumber : 조회 페이지 번호\n\n" +
                    "pageSize : 페이지당 내용 개수\n\n" +
                    "searchType : 검색어 검색 조건 => TITLE(제목), CONTENTS(내용), TITLE_CONTENTS(제목+내용);\n\n" +
                    "orderType : 정렬 조건 => VIEW(조회수), COMMENT(댓글), VOTE(공감수), DATE(날짜)\n\n" +
                    "orderBy : 정렬 차순 => ASC(오름차순), DESC(내림차순)\n\n" +
                    "keyword : 검색어\n\n" +
                    "informationType : 정보공간 타입 =>  THANKS(인사말), COMPLEX_VIEW(단지전경), TEL_INFO(연락처 정보), FACILITY(커뮤니티 시설), ETC(기타 사이트)\n\n" +
                    "categoryId : 정보공간 카테고리 ID\n\n" +
                    "apartmentId 를 제외한 나머지 값은 필수가 아니며, 포함하지 않으면 기본조건으로 처리하거나 영향을 주지 않습니다."
    )
    @GetMapping("/search/{apartmentId}")
    public ResponseEntity<?> searchInformation(
            @PathVariable Long apartmentId,
            @RequestParam(required = false, defaultValue = "1") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "TITLE") SearchType searchType,
            @RequestParam(required = false, defaultValue = "DATE") OrderType orderType,
            @RequestParam(required = false, defaultValue = "DESC") OrderBy orderBy,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) InformationType informationType,
            @RequestParam(required = false) Long categoryId){
        InformationDTO.InformationSearchReqDTO reqDTO = InformationDTO.InformationSearchReqDTO.builder()
                .apartmentId(apartmentId)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .searchType(searchType)
                .orderType(orderType)
                .orderBy(orderBy)
                .keyword(keyword)
                .informationType(informationType)
                .categoryId(categoryId)
                .build();
        return informationService.searchInformation(reqDTO);
    }

    @Operation(
            summary = "정보공간 조회 API",
            description = "informationId : 조회하려는 정보글 ID"
    )
    @GetMapping("/{informationId}")
    public ResponseEntity<?> getInformation(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long informationId){
        return informationService.getInformation(informationId,memberToken);
    }
}
