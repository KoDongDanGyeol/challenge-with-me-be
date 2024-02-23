package com.example.challengewithmebe.qna.controller;

import com.example.challengewithmebe.global.security.jwt.JwtProvider;
import com.example.challengewithmebe.qna.dto.QuestionDTO;
import com.example.challengewithmebe.qna.dto.QuestionPreviewDTO;
import com.example.challengewithmebe.qna.service.QnAService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final JwtProvider jwtProvider;

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

    // 특정 질문(1개)과 그에 대한 답변 조회
    @GetMapping("/{questionId}/detail")
    public ResponseEntity<QuestionDTO> getOneQuestion(@PathVariable Long questionId) {
        return ResponseEntity.ok(qnAService.findOneQuestion(questionId));
    }

    // 질문 생성
    @PostMapping(value = {"","/"})
    public ResponseEntity<Long> postQuestion(
            @RequestBody QuestionDTO question,
            HttpServletRequest request){
        Long memberId = Long.valueOf(jwtProvider.extractId(request));
        Long savedId = qnAService.addQuestion(question, memberId);
        return ResponseEntity.ok(savedId);
    }

    // 질문 삭제
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Boolean> deleteQuestion(@PathVariable Long questionId, HttpServletRequest request) {
        Long memberId = Long.valueOf(jwtProvider.extractId(request));
        Boolean completelyDeleted = qnAService.deleteOneQuestion(questionId, memberId);
        return ResponseEntity.ok(completelyDeleted);
    }

    // 질문 업데이트/수정
    @PutMapping(value = {"","/"})
    public ResponseEntity<Long> putQuestion(
            @RequestBody QuestionDTO question,
            HttpServletRequest request){
        Long memberId = Long.valueOf(jwtProvider.extractId(request));
        Long updatedId = qnAService.updateQuestion(question, memberId);
        return ResponseEntity.ok(updatedId);
    }

    // 모든 질문 페이지로 보기(미리보기)
    @GetMapping("/group")
    public ResponseEntity<Page<QuestionPreviewDTO>> getAllQuestionPreviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "all")String type,
            HttpServletRequest request) {
        Long memberId = Long.valueOf(jwtProvider.extractId(request));
        Page<QuestionPreviewDTO> questions = qnAService.findAllQuestionPreviews(page, size, type, memberId);
        return ResponseEntity.ok(questions);
    }

    // 특정 문제에 대한 페이지로 보기(미리보기)
    @GetMapping("/{problemId}/group")
    public ResponseEntity<Page<QuestionPreviewDTO>> getQuestionPreviewsForSpecificProblem(
            @PathVariable Long problemId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "all")String type,
            HttpServletRequest request) {
        Long memberId = Long.valueOf(jwtProvider.extractId(request));
        Page<QuestionPreviewDTO> questions = qnAService.findQuestionPreviewsForSpecificProblem(problemId, page, size, type, memberId);
        return ResponseEntity.ok(questions);
    }
}
