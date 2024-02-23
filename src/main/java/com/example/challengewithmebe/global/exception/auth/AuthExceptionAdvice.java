package com.example.challengewithmebe.global.exception.auth;

import com.example.challengewithmebe.global.exception.ResponseService;
import com.example.challengewithmebe.global.exception.dto.FailResult;
import com.example.challengewithmebe.global.exception.global.BadRequestException;
import com.example.challengewithmebe.global.exception.global.UnknownException;
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
public class AuthExceptionAdvice {
    private final MessageSource messageSource;
    private final ResponseService responseService;

    //0102
    @ExceptionHandler(NotValidTokenException.class)
    protected ResponseEntity<FailResult> NotValidTokenException(NotValidTokenException e){
        //log.info(getMessage("notValidToken.message"));
        return ResponseEntity.status(400).body(responseService.getFailResult(
                getMessage("notValidToken.code"),
                getMessage("notValidToken.message")));
    }

    //5100
    @ExceptionHandler(OwnerOnlyOperationException.class)
    protected ResponseEntity<FailResult> OwnerOnlyOperationException(OwnerOnlyOperationException e){
        return ResponseEntity.status(400).body(responseService.getFailResult(
                getMessage("ownerOnlyOperation.code"),
                getMessage("ownerOnlyOperation.message")));
    }

    private String getMessage(String code){
        return getMessage(code,null);
    }

    private String getMessage(String code,Object[] args){
        return messageSource.getMessage(code,args, LocaleContextHolder.getLocale());
    }
}
