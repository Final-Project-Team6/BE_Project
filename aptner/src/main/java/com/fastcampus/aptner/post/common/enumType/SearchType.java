package com.fastcampus.aptner.post.common.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchType {
    TITLE("제목"), CONTENTS("내용"), TITLE_CONTENTS("제목+내용");
    private final String SearchType;
}
