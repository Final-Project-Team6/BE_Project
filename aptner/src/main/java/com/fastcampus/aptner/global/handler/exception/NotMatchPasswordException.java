package com.fastcampus.aptner.global.handler.exception;

public class NotMatchPasswordException extends IllegalStateException {
    public NotMatchPasswordException(String message) {
        super(message);
    }
}
