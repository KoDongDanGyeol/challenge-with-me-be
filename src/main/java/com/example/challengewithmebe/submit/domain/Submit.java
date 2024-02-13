package com.example.challengewithmebe.submit.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Submit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long problemId; //문제id
    private Long memberId; //회원id
    private boolean isCorrect; //정답여부
    private String submitCode; //제출코드
    private String language; //언어
    private String status; //문제 상태?
}