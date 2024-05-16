package com.fastcampus.aptner.global.handler.exception;

import org.springframework.dao.DuplicateKeyException;

public class CustomDuplicationKeyException extends DuplicateKeyException {
    public CustomDuplicationKeyException(String message) {
        super(message);
    }
}
