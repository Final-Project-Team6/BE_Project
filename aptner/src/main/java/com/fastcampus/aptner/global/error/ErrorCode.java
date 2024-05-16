package com.fastcampus.aptner.global.error;

import org.springframework.http.HttpStatusCode;

public interface ErrorCode {
    String name();
    HttpStatusCode getHttpStatus();
    String getMessage();
}
