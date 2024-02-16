package com.example.challengewithmebe.chat.dto.request;


import com.example.challengewithmebe.chat.dto.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromptDTO {
    private String model;
    private double temperature;
    private List<MessageDTO> messages;
}
