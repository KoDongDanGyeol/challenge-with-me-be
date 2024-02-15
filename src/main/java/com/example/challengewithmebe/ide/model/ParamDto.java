package com.example.challengewithmebe.ide.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamDto {
    private TestcaseDto testcaseTypes;
    private List<TestcaseDto> testcaseValues;
}
