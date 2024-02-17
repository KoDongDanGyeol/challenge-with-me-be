package com.example.challengewithmebe.chat.dto.response.gpt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GPTResponse {
    private int created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
}
