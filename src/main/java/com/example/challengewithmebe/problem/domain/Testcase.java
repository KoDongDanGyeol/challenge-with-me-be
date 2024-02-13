package com.example.challengewithmebe.problem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
@Getter
public class Testcase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private String inputData;
    private String outputData;
    private Boolean isHidden;

    public void setProblem(Problem problem) {
        this.problem = problem;
        problem.getTestcases().add(this);
    }
}
