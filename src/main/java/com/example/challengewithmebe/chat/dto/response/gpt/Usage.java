package com.example.challengewithmebe.chat.dto.response.gpt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usage {
    private int prompt_token;
    private int completion_tokens;
    private int total_tokens;
}
