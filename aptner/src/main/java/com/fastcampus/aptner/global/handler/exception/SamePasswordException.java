package com.fastcampus.aptner.global.handler.exception;

public class SamePasswordException extends RuntimeException {

    public SamePasswordException(String message) {
        super(message);
    }
}
