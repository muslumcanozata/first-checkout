package com.bootcamp.firstcheckout.exceptions.base;

import com.bootcamp.firstcheckout.controllers.responses.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleRuntimeException(String code, String message) {
        log.error("[RuntimeException] {}", message);
        return ResponseEntity.internalServerError().body(new ErrorResponse(code, message));
    }
    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleApplicationException(String code, String message) {
        log.error("[ApplicationException] {}", message);
        return ResponseEntity.status(500).body(new ErrorResponse(code, message));
    }
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleValidationException(String code, String message) {
        log.error("[ValidationException] {}", message);
        return ResponseEntity.status(400).body(new ErrorResponse(code, message));
    }
}
