package com.fastcampus.aptner.global.handler.exception;

import org.springframework.dao.DuplicateKeyException;

public class DataDuplicateKeyException extends DuplicateKeyException {

    public DataDuplicateKeyException(String message) {
        super(message);
    }
}
