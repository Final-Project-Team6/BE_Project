package com.fastcampus.aptner.post.opinion.service;

import com.fastcampus.aptner.post.opinion.domain.VoteType;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface VoteService {
    ResponseEntity<HttpStatus> voteToPost(MemberTempDTO.MemberAuthDTO token, Long postId, VoteType voteType, Boolean opinion);

    ResponseEntity<HttpStatus> deleteVote(MemberTempDTO.MemberAuthDTO token, Long postId, VoteType voteType);
}
