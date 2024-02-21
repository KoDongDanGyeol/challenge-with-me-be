package com.example.challengewithmebe.global.exception.global.advice;

import com.example.challengewithmebe.global.exception.global.BadRequestException;
import com.example.challengewithmebe.global.exception.ResponseService;
import com.example.challengewithmebe.global.exception.global.UnknownException;
import com.example.challengewithmebe.global.exception.dto.FailResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionAdvice {
    private final MessageSource messageSource;
    private final ResponseService responseService;

    //0000
    @ExceptionHandler(UnknownException.class)
    protected ResponseEntity<FailResult> UnknownException(UnknownException e){
        return ResponseEntity.status(500).body(responseService.getFailResult(
                getMessage("unknown.code"),
                getMessage("unknown.message")));
    }

    //1100
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<FailResult> BadRequestException(BadRequestException e){
        return ResponseEntity.status(403).body(responseService.getFailResult(
                getMessage("badRequest.code"),
                getMessage("badRequest.message")));
    }


    private String getMessage(String code){
        return getMessage(code,null);
    }

    private String getMessage(String code,Object[] args){
        return messageSource.getMessage(code,args, LocaleContextHolder.getLocale());
    }
}
