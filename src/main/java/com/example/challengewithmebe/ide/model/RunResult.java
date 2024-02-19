package com.example.challengewithmebe.ide.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RunResult<T> {

    private T input;
    private String expected;
    private String output;
    private Long performance;
    private String errorMsg;
    private boolean isPassed;

    public RunResult(T input, String expected, String output, Long performance, boolean isPassed) {
        this.input = input;
        this.expected = expected;
        this.output = output;
        this.performance = performance;
        this.isPassed = isPassed;
    }

    public RunResult(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
