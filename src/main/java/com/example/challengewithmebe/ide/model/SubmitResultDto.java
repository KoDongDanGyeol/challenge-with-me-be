package com.example.challengewithmebe.ide.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubmitResultDto<T> {

    private T runResult;
    private T submitResult;
}
