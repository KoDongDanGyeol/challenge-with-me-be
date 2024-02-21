package com.example.challengewithmebe.chat.controller;


import com.example.challengewithmebe.chat.domain.Chat;
import com.example.challengewithmebe.chat.dto.MessageDTO;
import com.example.challengewithmebe.chat.dto.request.ChatRequest;
import com.example.challengewithmebe.chat.dto.response.ChatResponse;
import com.example.challengewithmebe.chat.dto.response.RoomId;
import com.example.challengewithmebe.chat.dto.response.gpt.GPTResponse;
import com.example.challengewithmebe.chat.service.ChatService;
import com.example.challengewithmebe.global.security.jwt.JwtProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatController {
    private final ChatService chatService;
    private final JwtProvider jwtProvider;

    @PostMapping( "/chat/{roomId}")
    public ResponseEntity<ChatRequest> chatGPT(
            @PathVariable Long roomId,
            @RequestBody ChatRequest request,
            HttpServletRequest httpServletRequest) throws JsonProcessingException {
        jwtProvider.extractId(httpServletRequest);

        ChatRequest response = chatService.makeChat(request, roomId);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/chat")
    public ResponseEntity<RoomId> makeChatRoom(HttpServletRequest request){
        Long memberId = Long.valueOf(jwtProvider.extractId(request));
        RoomId response = chatService.makeChatRoom(memberId);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/chat/{roomId}")
    public ResponseEntity<ChatResponse> getChat(
            @PathVariable Long roomId,
            HttpServletRequest request){
        Long memberId = Long.valueOf(jwtProvider.extractId(request));
        ChatResponse response = chatService.getChat(memberId,roomId);

        return ResponseEntity.ok().body(response);
    }
}
