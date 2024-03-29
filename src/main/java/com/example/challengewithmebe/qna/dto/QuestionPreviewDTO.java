package com.example.challengewithmebe.qna.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class QuestionPreviewDTO {
    private Long id;
    private Long problemId; // 문제 id
    private String problemTitle; // 문제 제목
    private String title;  // 질문 제목
    private Long memberId; // 작성자 id
    private int answerCounts; // 해당 질문에 대한 답변 개수
    private String name; // 해당 질문에 대한 답변 개수
    private String profileImgUrl; // 해당 질문에 대한 답변 개수
    private LocalDateTime createdAt; //생성일
    private LocalDateTime modifiedAt; //수정일
}
