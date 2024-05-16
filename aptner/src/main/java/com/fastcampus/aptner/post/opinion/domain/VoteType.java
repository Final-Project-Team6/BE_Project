package com.fastcampus.aptner.post.opinion.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VoteType {
    ANNOUNCEMENT("공지사항 공감"), COMPLAINT("민원 공감"), COMMUNICATION("소통 공간 공감"), COMMENT("댓글 공감");

    private final String VoteType;
}
