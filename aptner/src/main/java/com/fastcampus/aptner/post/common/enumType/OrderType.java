package com.fastcampus.aptner.post.common.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderType {
    VIEW("조회수"), COMMENT("댓글"), VOTE("공감수"), DATE("날짜");
    private final String OrderType;
}
