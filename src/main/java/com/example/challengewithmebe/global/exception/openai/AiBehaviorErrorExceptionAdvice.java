package com.example.challengewithmebe.global.exception.openai;

import com.example.challengewithmebe.global.exception.ResponseService;
import com.example.challengewithmebe.global.exception.auth.NotValidTokenException;
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
public class AiBehaviorErrorExceptionAdvice {
    private final MessageSource messageSource;
    private final ResponseService responseService;

    //9200
    @ExceptionHandler(AiBehaviorErrorException.class)
    protected ResponseEntity<FailResult> AiBehaviorErrorException(AiBehaviorErrorException e){
        return ResponseEntity.status(400).body(responseService.getFailResult(
                getMessage("aiBehaviorError.code"),
                getMessage("aiBehaviorError.message")));
    }


    private String getMessage(String code){
        return getMessage(code,null);
    }

    private String getMessage(String code,Object[] args){
        return messageSource.getMessage(code,args, LocaleContextHolder.getLocale());
    }
}
