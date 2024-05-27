package com.example.chatgptapi.service;

import com.example.chatgptapi.dto.*;
import com.example.chatgptapi.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GptV1Service implements GptService {

    private final GptProperties gptProperties;

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

    public GptChatResponse _chat(String prompt) {
        List<ChatMessage> messages = MessageUtil.getPrompt(prompt);

        return this.callChatApi(
                GptChatRequest.builder()
                        .model(gptProperties.getModel())
                        .messages(messages)
                        .temperature(gptProperties.getTemperature())
                        .build());
    }

    private GptChatResponse callChatApi(GptChatRequest request) {
        try {
            HttpClient httpClient = HttpClient.create()
                    .responseTimeout(Duration.ofSeconds(60000))
                    .keepAlive(false);

            // WebClient
            WebClient webClient = WebClient.builder()
                    .baseUrl(gptProperties.getBaseUrl())
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + gptProperties.getApiKey())
                    .clientConnector(new ReactorClientHttpConnector(httpClient))
                    .build();

            return webClient.post()
                    .uri(gptProperties.getChatUri())
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(
                            status -> status.value() < 200 || status.value() > 300,
                            r -> Mono.empty()
                    )
                    .bodyToMono(GptChatResponse.class)
                    .block();
        }
        catch (WebClientResponseException ex) {
            if (ex.getStatusCode() != HttpStatus.OK) {
                String bodyString = ex.getResponseBodyAsString();
                log.error(request.getClass() + " - WebClientResponseException = {}", bodyString);
            }
            return null;
        }
    }
}
