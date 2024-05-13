package com.fastcampus.aptner.post.common.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderBy {
    ASC("오름차순"),
    DESC("내림차순");
    private final String OrderBy;
}
