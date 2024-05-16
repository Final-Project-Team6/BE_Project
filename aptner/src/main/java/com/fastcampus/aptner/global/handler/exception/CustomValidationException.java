package com.fastcampus.aptner.global.handler.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class CustomValidationException extends RuntimeException {

    private final Map<String, String> errorMap;

    public CustomValidationException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }
}
