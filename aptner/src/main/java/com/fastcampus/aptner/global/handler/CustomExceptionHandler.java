package com.fastcampus.aptner.global.handler;

import com.fastcampus.aptner.global.handler.exception.*;
import com.fastcampus.aptner.member.dto.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CustomAPIException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> apiException(CustomAPIException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new HttpResponse<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomForbiddenException.class)
    public ResponseEntity<?> forbiddenException(CustomForbiddenException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new HttpResponse<>(-1, e.getMessage(), null), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> validationApiException(CustomValidationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new HttpResponse<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomDataNotFoundException.class)
    public ResponseEntity<?> handleDuplicateKeyException(CustomDataNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new HttpResponse<>(-1, e.getMessage(), null), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomDuplicationKeyException.class)
    public ResponseEntity<?> handleNotFoundException(CustomDuplicationKeyException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new HttpResponse<>(-1, e.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDataAccessException(DataAccessException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new HttpResponse<>(-1, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
