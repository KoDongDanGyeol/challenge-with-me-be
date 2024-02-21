package com.example.challengewithmebe.chat.service;


import com.example.challengewithmebe.chat.domain.Chat;
import com.example.challengewithmebe.chat.domain.ChatRoom;
import com.example.challengewithmebe.chat.dto.MessageDTO;
import com.example.challengewithmebe.chat.dto.request.ChatRequest;
import com.example.challengewithmebe.chat.dto.request.PromptDTO;
import com.example.challengewithmebe.chat.dto.response.ChatResponse;
import com.example.challengewithmebe.chat.dto.response.RoomId;
import com.example.challengewithmebe.chat.dto.response.gpt.Choice;
import com.example.challengewithmebe.chat.dto.response.gpt.GPTResponse;
import com.example.challengewithmebe.chat.repository.ChatRepository;
import com.example.challengewithmebe.chat.repository.ChatRoomRepository;
import com.example.challengewithmebe.global.exception.global.BadRequestException;
import com.example.challengewithmebe.global.exception.notExist.NotExistChatRoomException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    @Value("${openai.secretKey}")
    private String secretKey;

    @Value("${openai.model}")
    private String model;

    public HttpHeaders gptHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ secretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public List<MessageDTO> makeMessages(MessageDTO messageDTO){
        MessageDTO systemMessage = MessageDTO.builder()
                .role("system")
                .content("Acting as a senior developer advising students tackling coding tests.\n" +
                        "Put the response in JSON format with a string value " +
                        "having 'description' as the key. " +
                        "If the response includes code, " +
                        "place it under the key named 'code'." +
                        "Except for the code, all responses should be in Korean.")
                .build();

        List<MessageDTO> messages = new ArrayList<>();
        messages.add(systemMessage);
        messages.add(messageDTO);
        return messages;
    }

    public Choice makePrompt(MessageDTO messageDTO) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = gptHeaders();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<MessageDTO> messages = makeMessages(messageDTO);

        PromptDTO requestBody = PromptDTO.builder()
                .temperature(0.4)
                .model(model)
                .messages(messages)
                .build();

        HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, headers);
        String url = "https://api.openai.com/v1/chat/completions";

        ResponseEntity<Object> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Object.class
        );

        String jsonEntity = objectMapper.writeValueAsString(responseEntity.getBody());
        GPTResponse gptresponse = objectMapper.readValue(jsonEntity, GPTResponse.class);
        Choice choice = gptresponse.getChoices().get(0);

        return choice;
    }

    public RoomId makeChatRoom(Long memberId){
        ChatRoom chatRoom = ChatRoom.builder()
                .memberId(memberId)
                .build();
        ChatRoom newRoom = chatRoomRepository.save(chatRoom);

        return RoomId.builder()
                .roomId(newRoom.getId())
                .build();
    }

    public ChatRequest makeChat(ChatRequest request, Long roomId) throws JsonProcessingException {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(NotExistChatRoomException::new);
        Chat newChat = Chat.builder()
                .chatRoom(chatRoom)
                .message(request.getMessage())
                .sender(request.getSender())
                .build();

        Chat savedChat = chatRepository.save(newChat);

        MessageDTO messageDTO = MessageDTO.builder()
                .role(savedChat.getSender())
                .content(savedChat.getMessage())
                .build();

        Choice choice = makePrompt(messageDTO);

        Chat answer = Chat.builder()
                .sender("gpt")
                .chatRoom(chatRoom)
                .message(choice.getMessage().getContent())
                .build();
        Chat savedAnswer = chatRepository.save(answer);

        ChatRequest response = ChatRequest.builder()
                .chatRoomId(savedAnswer.getChatRoom().getId())
                .message(savedAnswer.getMessage())
                .sender(savedAnswer.getSender())
                .sendTime(savedAnswer.getCreatedAt())
                .build();

        return response;
    }

    public ChatResponse getChat(Long memberId, Long roomId){
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(NotExistChatRoomException::new);
        if(!chatRoom.getMemberId().equals(memberId)){
            throw new BadRequestException();
        }

        List<Chat> chatPage = chatRepository.findAllByChatRoomIdOrderByCreatedAt(roomId);

        List<ChatRequest> chatList = new ArrayList<>();

        for(Chat chat : chatPage){
            chatList.add(ChatRequest.builder()
                    .chatRoomId(chat.getChatRoom().getId())
                    .sender(chat.getSender())
                    .message(chat.getMessage())
                    .sendTime(chat.getCreatedAt())
                    .build());
        }
        ChatResponse response = ChatResponse.builder()
                .memberId(chatRoom.getMemberId())
                .roomId(chatRoom.getId())
                .messages(chatList)
                .build();
        return response;
    }

}
