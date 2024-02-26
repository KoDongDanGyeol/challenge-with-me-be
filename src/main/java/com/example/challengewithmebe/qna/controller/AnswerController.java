package com.example.challengewithmebe.qna.controller;

import com.example.challengewithmebe.global.security.jwt.JwtProvider;
import com.example.challengewithmebe.qna.dto.AnswerDTO;
import com.example.challengewithmebe.qna.service.QnAService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final QnAService qnAService;
    private final JwtProvider jwtProvider;

    //특정 질문에 대한 답변 조회
    @GetMapping("/{questionId}")
    public ResponseEntity<List<AnswerDTO>> getAnswersForOneQuestion(@PathVariable Long questionId){
        return ResponseEntity.ok(qnAService.answersForOneQuestion(questionId));
    }

    // 답변 생성
    @PostMapping(value = {"","/"})
    public ResponseEntity<Long> postAnswer(
            @RequestBody AnswerDTO answerDTO,
            HttpServletRequest request){
        jwtProvider.isLoggedIn(request);
        Long memberId = Long.valueOf(jwtProvider.extractId(request));
        Long savedId = qnAService.addAnswer(answerDTO,memberId);
        return ResponseEntity.ok(savedId);
    }

    // 답변 삭제
    @DeleteMapping("/{answerId}")
    public ResponseEntity<Boolean> deleteAnswer(@PathVariable Long answerId, HttpServletRequest request) {
        jwtProvider.isLoggedIn(request);
        Long memberId = Long.valueOf(jwtProvider.extractId(request));
        return ResponseEntity.ok(qnAService.deleteOneAnswer(answerId,memberId));
    }

    // 답변 업데이트/수정
    @PutMapping(value = {"","/"})
    public ResponseEntity<Long> putAnswer(
            @RequestBody AnswerDTO answer,
            HttpServletRequest request){
        jwtProvider.isLoggedIn(request);
        Long memberId = Long.valueOf(jwtProvider.extractId(request));
        Long updatedId = qnAService.updateAnswer(answer, memberId);
        return ResponseEntity.ok(updatedId);
    }
}