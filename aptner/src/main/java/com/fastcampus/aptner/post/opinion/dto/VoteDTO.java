package com.fastcampus.aptner.post.opinion.dto;

public class VoteDTO {
    public record VoteRespDTO(int total, int agree, int disagree, Boolean yourVote) {
    }
}
