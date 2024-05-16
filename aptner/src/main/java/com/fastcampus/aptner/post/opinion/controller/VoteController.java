package com.fastcampus.aptner.post.opinion.controller;

import com.fastcampus.aptner.member.domain.RoleName;
import com.fastcampus.aptner.post.opinion.domain.VoteType;
import com.fastcampus.aptner.post.opinion.service.VoteService;
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
@RequestMapping("/api/post/vote/")
@Tag(name = "공감 기능", description = "공감 생성, 공감 삭제")
public class VoteController {

    private final VoteService voteService;

    private MemberTempDTO.MemberAuthDTO memberTempToken = new MemberTempDTO.MemberAuthDTO(1L, RoleName.ADMIN,1L);

    @Operation(
            summary = "공감/비공감 생성 API",
            description = "postId : 공감/비공감 생성하려는 게시글 ID\n\n" +
                    "voteType : 공감/비공감 하려는 게시글 타입 ANNOUNCEMENT(공지사항 공감), COMPLAINT(민원 공감), COMMUNICATION(소통 공간 공감), COMMENT(댓글 공감)\n\n" +
                    "opinion : 공감(true) 비공감 (false)"
    )
    @PostMapping("/{postId}")
    public ResponseEntity<HttpStatus> voteToPost(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long postId,
            @RequestParam VoteType voteType,
            @RequestParam Boolean opinion){
        return voteService.voteToPost(memberTempToken,postId,voteType,opinion);
    }

    @Operation(
            summary = "공감/비공감 삭제 API",
            description = "postId : 공감/비공감 삭제하려는 게시글 ID\n\n" +
                    "voteType : 공감/비공감 삭제 하려는 게시글 타입 ANNOUNCEMENT(공지사항 공감), COMPLAINT(민원 공감), COMMUNICATION(소통 공간 공감), COMMENT(댓글 공감)"
    )
    @DeleteMapping("/{postId}")
    public ResponseEntity<HttpStatus> deleteVote(
            @AuthenticationPrincipal MemberTempDTO.MemberAuthDTO memberToken,
            @PathVariable Long postId,
            @RequestParam VoteType voteType){
        return voteService.deleteVote(memberTempToken,postId,voteType);
    }
}
