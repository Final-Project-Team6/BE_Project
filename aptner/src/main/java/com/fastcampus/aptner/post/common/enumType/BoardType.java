package com.fastcampus.aptner.post.common.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoardType {
    ANNOUNCEMENT("공지사항"),
    COMPLAINT("민원"),
    COMMUNICATION("소통 공간"),
    INFORMATION("아파트 정보");
    private final String BoardType;

}
