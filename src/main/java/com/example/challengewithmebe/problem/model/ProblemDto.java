package com.example.challengewithmebe.problem.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProblemDto {
    private Long id;
    private String title;
    private String type;
    private String past;
    private String description;
    private List<TestcaseInfo> testcases;


}
