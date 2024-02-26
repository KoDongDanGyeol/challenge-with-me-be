package com.example.challengewithmebe.submit.domain;

import com.example.challengewithmebe.global.app.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Submit extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long problemId; //문제id
    private Long memberId; //회원id
    private boolean isCorrect; //정답여부
    private String submitCode; //제출코드
    private String language; //언어
    private String status; //문제 상태?

    public Submit(Long problemId, Long memberId, boolean isCorrect, String code) {
        this.problemId = problemId;
        this.memberId = memberId;
        this.isCorrect = isCorrect;
        this.submitCode = code;
    }
}
