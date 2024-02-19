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
public class PromptDTO {
    private String model;
    private double temperature;
    private List<MessageDTO> messages;
    private int max_tokens;

    private ResponseFormat response_format;
    @Getter
    static class ResponseFormat {
        private String type;

        public ResponseFormat() {
            this.type = "json_object";
        }
    }

    @Builder
    public PromptDTO(String model, double temperature, List<MessageDTO> messages) {
        this.model = model;
        this.temperature = temperature;
        this.messages = messages;
        this.max_tokens = 500;
        this.response_format = new ResponseFormat();
    }
}



