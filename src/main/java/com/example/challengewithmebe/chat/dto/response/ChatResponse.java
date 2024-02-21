package com.example.challengewithmebe.chat.dto.response;

import com.example.challengewithmebe.chat.domain.Chat;
import com.example.challengewithmebe.chat.dto.request.ChatRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ChatResponse {
    private Long roomId;
    private Long memberId;
    private List<ChatRequest> messages = new ArrayList<>();
}
