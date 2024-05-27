package com.example.chatgptapi.service;

import com.example.chatgptapi.feign.GptFeignClient;
import com.example.chatgptapi.dto.*;
import com.example.chatgptapi.util.MessageUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GptV3Service implements GptService {

    // https://platform.openai.com/docs/api-reference/chat/create
    private final GptProperties gptProperties;
    private final GptFeignClient gptFeignClient;
    private final ObjectMapper objectMapper;

    @Override
    public List<ChatMessage> chat(List<ChatMessage> messages, String prompt) {
        MessageUtil.getPrompt(messages, prompt);

        try {
            GptChatResponse response = this.callChatApi(
                    GptChatRequest.builder()
                            .model(gptProperties.getModel())
                            .messages(messages)
                            .temperature(gptProperties.getTemperature())
                            .build());

            messages.addAll(
                    response.getChoices()
                            .stream()
                            .map(Choice::getMessage)
                            .filter(ObjectUtils::isNotEmpty)
                            .toList());

            return messages;

        } catch (RuntimeException e) {
            log.error("Unexpected error: {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    private GptChatResponse callChatApi(GptChatRequest request) {
        return gptFeignClient.chat(request);
    }

    public GptChatResponse _chat(String prompt) {
        List<ChatMessage> messages = MessageUtil.getPrompt(prompt);

        return this._callChatApi(
                GptChatRequest.builder()
                        .model(gptProperties.getModel())
                        .messages(messages)
                        .temperature(gptProperties.getTemperature())
                        .build());
    }

    private GptChatResponse _callChatApi(GptChatRequest request) {
        ResponseEntity<String> response = gptFeignClient.chatToString(request);

        // objectMapper configure
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        GptChatResponse chatResponse = new GptChatResponse();

        try {
            String bodyString = response.getBody();
            chatResponse = objectMapper.readValue(bodyString, GptChatResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return chatResponse;
    }
}
