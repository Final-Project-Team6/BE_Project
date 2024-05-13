package com.fastcampus.aptner.post.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostStatus {
    PUBLISHED("게시됨"),
    HIDDEN("숨김"),
    DELETED("삭제됨"),
    FORCE_DELETED("강제 삭제됨");
    private final String PostStatus;
}
