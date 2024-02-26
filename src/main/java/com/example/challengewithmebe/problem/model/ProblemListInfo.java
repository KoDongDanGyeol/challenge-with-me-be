package com.example.challengewithmebe.problem.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter @Setter
public class ProblemListInfo {
    private Page<ProblemListDto> problems;
    private List<String> pasts;

    public ProblemListInfo(Page<ProblemListDto> problemListDtos, List<String> allPasts) {
        this.problems = problemListDtos;
        this.pasts = allPasts;
    }
}
