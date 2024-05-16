package com.fastcampus.aptner.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RestAPIException extends RuntimeException{
    private final ErrorCode errorCode;
}
