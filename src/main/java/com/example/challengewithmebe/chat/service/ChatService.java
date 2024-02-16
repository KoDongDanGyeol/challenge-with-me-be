package com.example.challengewithmebe.chat.service;


import com.example.challengewithmebe.chat.dto.MessageDTO;
import com.example.challengewithmebe.chat.dto.request.PromptDTO;
import com.example.challengewithmebe.chat.dto.response.gpt.GPTResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

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

    public GPTResponse makePrompt(MessageDTO messageDTO) throws JsonProcessingException {
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

        String jsonEntity = objectMapper.writeValueAsString(responseEntity.getBody()); //에러 추가
        GPTResponse response = objectMapper.readValue(jsonEntity, GPTResponse.class);
        return response;
    }

}
