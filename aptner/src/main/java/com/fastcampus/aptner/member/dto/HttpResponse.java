package com.fastcampus.aptner.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class HttpResponse<T> {
    // TODO: Record 클래스으로 변환 가능하다.
    private final Integer code; // 1성공, 실패
    private final String message;
    private final T data;
}
