package com.example.chatgptapi.restClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HttpInterfaceConfig {

    @Value("${chatgpt.api-key}")
    private String apiKey = "";
    private final String baseUrl ="https://api.openai.com";
    private final HttpInterfaceFactory httpInterfaceFactory;

    //https://myvelop.tistory.com/217#Spring%206.1.2%20%EB%B2%84%EC%A0%84%EC%97%90%20%EC%B6%9C%EC%8B%9C%EB%90%9C%20RestClient-1
    //https://medium.com/@auburn0820/spring-6-0%EC%9D%98-httpinterface-%EB%81%84%EC%A0%81%EC%9D%B4%EA%B8%B0-f3653143a373
    public HttpInterfaceConfig() {
        this.httpInterfaceFactory = new SimpleHttpInterfaceFactory();
    }

    @Bean
    public GptRestClient GptRestClient() {
        return httpInterfaceFactory.create(GptRestClient.class, this.createRestClient());
    }

    private RestClient createRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultStatusHandler(
                        HttpStatusCode::is4xxClientError,
                        (request, response) -> {
                            log.error("Client Error Code={}", response.getStatusCode());
                            log.error("Client Error Message={}", new String(response.getBody().readAllBytes()));
                        })
                .defaultStatusHandler(
                        HttpStatusCode::is5xxServerError,
                        (request, response) -> {
                            log.error("Server Error Code={}", response.getStatusCode());
                            log.error("Server Error Message={}", new String(response.getBody().readAllBytes()));
                        })
                .build();
    }
}
