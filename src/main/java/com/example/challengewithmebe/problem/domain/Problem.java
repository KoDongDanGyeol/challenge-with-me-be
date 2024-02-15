package com.example.challengewithmebe.problem.domain;

import com.example.challengewithmebe.global.app.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
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

    @OneToMany(mappedBy = "problem")
    private List<Testcase> testcases = new ArrayList<>();
}
