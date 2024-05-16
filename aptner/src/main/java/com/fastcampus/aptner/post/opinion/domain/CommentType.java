package com.fastcampus.aptner.post.opinion.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentType {
    ANNOUNCEMENT("공지사항 댓글"), COMPLAINT("민원 댓글"), COMMUNICATION("소통 공간 댓글"), REPLY("대댓글");
    private final String CommentType;

}
