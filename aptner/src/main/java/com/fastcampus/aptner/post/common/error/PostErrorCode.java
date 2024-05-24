package com.fastcampus.aptner.post.common.error;

import com.fastcampus.aptner.global.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {
    NOT_SAME_USER(HttpStatus.FORBIDDEN, "The author and the user are not the same"),
    NOT_ALLOWED_APARTMENT(HttpStatus.FORBIDDEN, "The apartment access is not permitted"),
    CANT_DELETE(HttpStatus.BAD_REQUEST, "Cant delete because it contains post");

    private final HttpStatus httpStatus;
    private final String message;
}
