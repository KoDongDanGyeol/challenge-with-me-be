package com.example.challengewithmebe.problem.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Testcase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="problem_id")
    private Problem problem;
    private String inputData;
    private String outputData;
    private Boolean isHidden;



    public void setProblem(Problem problem) {
        this.problem = problem;
        problem.getTestcases().add(this);
    }
}
