package com.example.challengewithmebe.problem.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TestCaseValues {
    private List<String> inputData;
    private String outputData;



}
