package com.example.challengewithmebe.ide.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubmitResultDto<T> {
    private String submitType;
    private T runResult;
    private T submitResult;
}
