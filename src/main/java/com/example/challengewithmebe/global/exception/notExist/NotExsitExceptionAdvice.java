package com.example.challengewithmebe.global.exception.notExist;

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
public class NotExsitExceptionAdvice {
    private final MessageSource messageSource;
    private final ResponseService responseService;

    //1300
    @ExceptionHandler(NotExistMemberException.class)
    protected ResponseEntity<FailResult> NotExistMemberException(NotExistMemberException e){
        return ResponseEntity.status(404).body(responseService.getFailResult(
                getMessage("notExistMember.code"),
                getMessage("notExistMember.message")));
    }

    //2300
    @ExceptionHandler(NotExistChallengeException.class)
    protected ResponseEntity<FailResult> NotExistChallengeException(NotExistChallengeException e){
        return ResponseEntity.status(404).body(responseService.getFailResult(
                getMessage("notExistChallenge.code"),
                getMessage("notExistChallenge.message")));
    }

    //6300
    @ExceptionHandler(NotExistSubmitException.class)
    protected ResponseEntity<FailResult> NotExistSubmitException(NotExistSubmitException e){
        return ResponseEntity.status(404).body(responseService.getFailResult(
                getMessage("notExistSubmit.code"),
                getMessage("notExistSubmit.message")));
    }

    //8300
    @ExceptionHandler(NotExistCommentException.class)
    protected ResponseEntity<FailResult> NotExistCommentException(NotExistCommentException e){
        return ResponseEntity.status(404).body(responseService.getFailResult(
                getMessage("notExistComment.code"),
                getMessage("notExistComment.message")));
    }

    //5300
    @ExceptionHandler(NotExistQuestionException.class)
    protected ResponseEntity<FailResult> NotExistQuestionException(NotExistQuestionException e){
        return ResponseEntity.status(404).body(responseService.getFailResult(
                getMessage("notExistQuestion.code"),
                getMessage("notExistQuestion.message")));
    }

    //7300
    @ExceptionHandler(NotExistAnswerException.class)
    protected ResponseEntity<FailResult> NotExistMemberException(NotExistAnswerException e){
        return ResponseEntity.status(404).body(responseService.getFailResult(
                getMessage("notExistAnswer.code"),
                getMessage("notExistAnswer.message")));
    }

    //9300
    @ExceptionHandler(NotExistChatRoomException.class)
    protected ResponseEntity<FailResult> NotExistChatRoomException(NotExistChatRoomException e){
        //log.info(getMessage("notExistChatRoom.message"));
        return ResponseEntity.status(404).body(responseService.getFailResult(
                getMessage("notExistChatRoom.code"),
                getMessage("notExistChatRoom.message")));
    }


    private String getMessage(String code){
        return getMessage(code,null);
    }

    private String getMessage(String code,Object[] args){
        return messageSource.getMessage(code,args, LocaleContextHolder.getLocale());
    }
}
