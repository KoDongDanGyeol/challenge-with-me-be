package com.example.challengewithmebe.solution.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SolutionDTO {
    private Long id;
    private Long problemId; // 문제 ID
    private Long memberId; // 회원 ID
    private boolean isCorrect; // 정답 여부
    private String submitCode; // 제출 코드
    private String language; // 언어
    private String status; // 문제 상태
    private LocalDateTime createdAt; //생성일
    private LocalDateTime modifiedAt; //수정일

    private String name; // 회원 이름
    private String imgUrl; //프로필 이미지 링크
}
