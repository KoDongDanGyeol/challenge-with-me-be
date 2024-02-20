package com.example.challengewithmebe.qna.controller;

import com.example.challengewithmebe.qna.domain.Answer;
import com.example.challengewithmebe.qna.dto.AnswerDTO;
import com.example.challengewithmebe.qna.dto.QuestionDTO;
import com.example.challengewithmebe.qna.service.QnAService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final QnAService qnAService;

    //특정 질문에 대한 답변 조회
    @GetMapping("/{questionId}")
    public ResponseEntity<List<AnswerDTO>> getAnswersForOneQuestion(@PathVariable Long questionId){
        return ResponseEntity.ok(qnAService.answersForOneQuestion(questionId));
    }

    // 답변 생성
    @PostMapping(value = {"","/"})
    public ResponseEntity<Long> postAnswer(@RequestBody AnswerDTO answerDTO){
        Long savedId = qnAService.addAnswer(answerDTO);
        return ResponseEntity.ok(savedId);
    }

    // 답변 삭제
    @DeleteMapping("/{answerId}")
    public ResponseEntity<Boolean> deleteAnswer(@PathVariable Long answerId) {
        return ResponseEntity.ok(qnAService.deleteOneAnswer(answerId));
    }

    // 질문 업데이트/수정
    @PutMapping(value = {"","/"})
    public ResponseEntity<Long> putAnswer(
            @RequestBody AnswerDTO answer){
        Long updatedId = qnAService.updateAnswer(answer);
        return ResponseEntity.ok(updatedId);
    }
}