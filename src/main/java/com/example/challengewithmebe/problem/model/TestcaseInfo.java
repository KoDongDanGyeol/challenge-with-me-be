package com.example.challengewithmebe.problem.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class TestcaseInfo {
    private TestcaseTypes testcaseTypes;
    private List<TestCaseValues> testcaseValues;
    private int hiddenTestcaseCount;

}
