package com.example.challengewithmebe.problem.domain;

import com.example.challengewithmebe.global.domain.BaseEntity;
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
public class Problem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type; //유형
    private String level; //난이도
    private String source; //출처
    private String past; //기출 -> 다른 좋은 이름을 생각해보자..
    private String title; //제목=이름
    private String imgUrl; //이미지url
    private String description; //설명
}
