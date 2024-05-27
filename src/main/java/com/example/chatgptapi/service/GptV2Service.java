package com.example.chatgptapi.service;

import com.example.chatgptapi.restClient.GptRestClient;
import com.example.chatgptapi.dto.*;
import com.example.chatgptapi.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GptV2Service implements GptService{

    private final GptProperties gptProperties;
    private final GptRestClient gptRestClient;

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

            if(ObjectUtils.isNotEmpty(response)) {
                messages.addAll(
                        response.getChoices()
                                .stream()
                                .map(Choice::getMessage)
                                .filter(ObjectUtils::isNotEmpty)
                                .toList());
            }

            return messages;

        } catch (RuntimeException e) {
            log.error("Unexpected error: {}", e.getMessage());
        }
        return new ArrayList<>();
    }

    private GptChatResponse callChatApi(GptChatRequest request) {
        return gptRestClient.chat(request);
    }

}
