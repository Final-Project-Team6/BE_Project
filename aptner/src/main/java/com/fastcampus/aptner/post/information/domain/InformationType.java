package com.fastcampus.aptner.post.information.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InformationType {

    THANKS("인사말"), COMPLEX_VIEW("단지전경"), TEL_INFO("연락처 정보"), FACILITY("커뮤니티 시설"), ETC("기타 사이트");
    private final String informationType ;
}
