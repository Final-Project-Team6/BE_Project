package com.fastcampus.aptner.post.common.error;

import com.fastcampus.aptner.global.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum VoteErrorCode implements ErrorCode {
    ALREADY_EXiSTS(HttpStatus.BAD_REQUEST, "Your vote about this post is already exists");

    private final HttpStatus httpStatus;
    private final String message;
}