package com.example.challengewithmebe.ide.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RunResultDto<T> {
    private T runResult;
}
