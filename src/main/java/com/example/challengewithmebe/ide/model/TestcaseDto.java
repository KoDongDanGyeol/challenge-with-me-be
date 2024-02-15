package com.example.challengewithmebe.ide.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class TestcaseDto {
    private List<String> input;
    private String expected;
}
