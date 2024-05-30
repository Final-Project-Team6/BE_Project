package com.fastcampus.aptner.post.complaint.controller;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.common.enumType.OrderBy;
import com.fastcampus.aptner.post.common.enumType.OrderType;
import com.fastcampus.aptner.post.common.enumType.SearchType;
import com.fastcampus.aptner.post.complaint.domain.ComplaintType;
import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import com.fastcampus.aptner.post.complaint.service.ComplaintService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/complaint/")
@Tag(name = "민원(사용자)", description = "")
public class ComplaintController {

    private final ComplaintService complaintService;

    @Operation(
            summary = "민원 생성 API",
            description = "Schema -> 민원 생성 \n\n apartmentId : 현재 사용중인 아파트 ID "
    )
    @PostMapping(value = "/{apartmentId}")
    public ResponseEntity<HttpStatus> uploadComplaint(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long apartmentId,
            @RequestBody ComplaintDTO.ComplaintReqDTO dto) {
        return complaintService.uploadComplaint(memberToken, apartmentId, dto);
    }

    @Operation(
            summary = "민원 수정 API",
            description = "Schema -> 민원 생성\n\n complaintId : 수정하려는 민원 ID"
    )
    @PatchMapping(value = "/{complaintId}")
    public ResponseEntity<HttpStatus> updateComplaint(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long complaintId,
            @RequestBody ComplaintDTO.ComplaintReqDTO dto) {
        return complaintService.updateComplaint(memberToken, complaintId, dto);
    }

    @Operation(
            summary = "민원 삭제 API",
            description = "complaintId : 삭제하려는 민원 ID"
    )
    @DeleteMapping(value = "/{complaintId}")
    public ResponseEntity<HttpStatus> deleteComplaint(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long complaintId) {
        return complaintService.deleteComplaint(memberToken, complaintId);
    }

    @Operation(
            summary = "민원 조회 API",
            description = "complaintId : 조회하려는 민원 ID"
    )
    @GetMapping("/{complaintId}")
    public ResponseEntity<?> getComplaint(
            @AuthenticationPrincipal JWTMemberInfoDTO request,
            @PathVariable Long complaintId) {
        return complaintService.getComplaint(request, complaintId);
    }

    @Operation(
            summary = "민원 목록 조회 API",
            description = "apartmentId : 현재 사용중인 아파트 ID \n\n" +
                    "pageNumber : 조회 페이지 번호\n\n" +
                    "pageSize : 페이지당 내용 개수\n\n" +
                    "searchType : 검색어 검색 조건 => TITLE(제목), CONTENTS(내용), TITLE_CONTENTS(제목+내용);\n\n" +
                    "orderType : 정렬 조건 => VIEW(조회수), COMMENT(댓글), VOTE(공감수), DATE(날짜)\n\n" +
                    "orderBy : 정렬 차순 => ASC(오름차순), DESC(내림차순)\n\n" +
                    "keyword : 검색어\n\n" +
                    "complaintType : 민원 타입 =>  MANAGEMENT_OFFICE(관리사무소), RESIDENTS_COMMITTEE(입주자 대표회의)\n\n" +
                    "categoryId : 공지사항 카테고리 ID\n\n" +
                    "myComplaint : 내 민원글만 보이기 => true 내 민원글만 반환, false & null 조건 처리 X\n\n" +
                    "period : 선택한 날짜부터 오늘까지의 글만 필터 ex) 2025-05-11\n\n"+
                    "apartmentId 를 제외한 나머지 값은 필수가 아니며, 포함하지 않으면 기본조건으로 처리하거나 영향을 주지 않습니다."
    )
    @GetMapping("/search/{apartmentId}")
    public ResponseEntity<?> searchComplaint(
            @AuthenticationPrincipal JWTMemberInfoDTO memberToken,
            @PathVariable Long apartmentId,
            @RequestParam(required = false, defaultValue = "1") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "TITLE_CONTENTS") SearchType searchType,
            @RequestParam(required = false, defaultValue = "DATE") OrderType orderType,
            @RequestParam(required = false, defaultValue = "DESC") OrderBy orderBy,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) ComplaintType complaintType,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean myComplaint,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate period){
        ComplaintDTO.ComplaintSearchReqDTO reqDTO = ComplaintDTO.ComplaintSearchReqDTO.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .apartmentId(apartmentId)
                .searchType(searchType)
                .orderType(orderType)
                .orderBy(orderBy)
                .keyword(keyword)
                .complaintType(complaintType)
                .categoryId(categoryId)
                .myComplaint(myComplaint)
                .period(period)
                .build();
        return complaintService.searchComplaint(reqDTO, memberToken);
    }

}
