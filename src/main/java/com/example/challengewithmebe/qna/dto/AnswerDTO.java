package com.example.challengewithmebe.qna.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AnswerDTO {

    private Long id;
    private Long questionId; // 질문의 id
    private String content; // 내용
    private Long memberId;
    private String name; // 작성자 이름
    private String profileImgUrl; // 작성자 프로필 이미지 url
    private LocalDateTime createdAt; //생성일
    private LocalDateTime modifiedAt; //수정일

}
