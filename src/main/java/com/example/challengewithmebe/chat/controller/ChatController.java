package com.example.challengewithmebe.chat.controller;


import com.example.challengewithmebe.chat.dto.MessageDTO;
import com.example.challengewithmebe.chat.dto.response.gpt.GPTResponse;
import com.example.challengewithmebe.chat.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatController {
    private final ChatService chatService;

    @PostMapping( "/chat")
    public ResponseEntity<GPTResponse> chatGPT(@RequestBody MessageDTO request) throws JsonProcessingException {
        MessageDTO fullrequest = MessageDTO.builder()
                .role(request.getRole())
                .content(request.getContent())
                .build();

        GPTResponse response = chatService.makePrompt(fullrequest);

        return ResponseEntity.ok().body(response);
    }
}
