package com.example.challengewithmebe.chat.dto.response.gpt;

import com.example.challengewithmebe.chat.dto.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Choice {
    private String finish_reason;
    private int index;
    private MessageDTO message;
}
