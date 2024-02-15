package com.example.challengewithmebe.ide.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamDto {
    private List<Object> paramTypes;
    private List<Object> testcases;
    private List<Object> result;
}
