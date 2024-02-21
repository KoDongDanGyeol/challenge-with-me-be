package com.example.challengewithmebe.global.exception.valid;

import com.example.challengewithmebe.global.exception.dto.ValidFailResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ValidationExceptionAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidFailResult> handleValidationException(MethodArgumentNotValidException e) {
        return ValidFailResult.toResult(e);
    }
}
