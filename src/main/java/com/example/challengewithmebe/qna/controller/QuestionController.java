package com.example.challengewithmebe.qna.controller;

import com.example.challengewithmebe.qna.dto.AnswerDTO;
import com.example.challengewithmebe.qna.dto.QuestionDTO;
import com.example.challengewithmebe.qna.dto.QuestionPreviewDTO;
import com.example.challengewithmebe.qna.service.QnAService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QnAService qnAService;

    //특정 챌린지 대상이 아닌 모든 질문과 답변 조회
    @GetMapping(value = {"/",""})
    public ResponseEntity<List<QuestionDTO>> getAllQuestions(){
        return ResponseEntity.ok(qnAService.allQuestions());

    }

    // 특정 챌린지에 대한 질문과 답변 조회
    @GetMapping("/{problemId}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsForOneProblem(@PathVariable Long problemId) throws Exception {
        return ResponseEntity.ok(qnAService.questionForOneProblem(problemId));
    }

    // 질문 생성
    @PostMapping(value = {"","/"})
    public ResponseEntity<Long> postQuestion(
            @RequestBody QuestionDTO question){
        Long savedId = qnAService.addQuestion(question);
        return ResponseEntity.ok(savedId);
    }

    // 질문 삭제
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Boolean> deleteQuestion(@PathVariable Long questionId) {
        return ResponseEntity.ok(qnAService.deleteOneQuestion(questionId));
    }

    // 질문 업데이트/수정
    @PutMapping(value = {"","/"})
    public ResponseEntity<Long> putQuestion(
            @RequestBody QuestionDTO question){
        Long updatedId = qnAService.updateQuestion(question);
        return ResponseEntity.ok(updatedId);
    }

    // 모든 질문 페이지로 보기(미리보기)
    @GetMapping("/group")
    public ResponseEntity<Page<QuestionPreviewDTO>> getAllQuestionPreviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<QuestionPreviewDTO> questions = qnAService.findAllQuestionPreviews(page, size);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{problemId}/group")
    public ResponseEntity<Page<QuestionPreviewDTO>> getQuestionPreviewsForSpecificProblem(
            @PathVariable Long problemId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<QuestionPreviewDTO> questions = qnAService.findQuestionPreviewsForSpecificProblem(problemId, page, size);
        return ResponseEntity.ok(questions);
    }
}
