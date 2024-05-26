package com.fastcampus.aptner.post.opinion.service;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.opinion.domain.VoteType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface VoteService {
    ResponseEntity<HttpStatus> voteToPost(JWTMemberInfoDTO token, Long postId, VoteType voteType, Boolean opinion);

    ResponseEntity<HttpStatus> deleteVote(JWTMemberInfoDTO token, Long postId, VoteType voteType);
    ResponseEntity<HttpStatus> updateVote(JWTMemberInfoDTO token, Long postId, VoteType voteType, Boolean opinion);
}
