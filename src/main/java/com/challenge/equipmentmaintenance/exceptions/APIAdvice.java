package com.challenge.equipmentmaintenance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class APIAdvice {

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<ExceptionDetails> handleBadRequestException(BadRequestException badRequestException) {
        return new ResponseEntity<>(ExceptionDetails.builder()
                .title("Bad Request Exception")
                .details(badRequestException.getMessage())
                .message(badRequestException.getClass().getName())
                .status(HttpStatus.BAD_REQUEST.value())
                .date(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

}
