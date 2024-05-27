package com.example.chatgptapi.feign;

import feign.Logger;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class GptFeignClientConfig {

    @Value("${chatgpt.api-key}")
    private String apiKey = "";

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            //"Authorization: Bearer $OPENAI_API_KEY"
            requestTemplate.header("Authorization", "Bearer " + apiKey);
            requestTemplate.header("Content-Type", "application/json");
        };
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

//    @Bean
//    ErrorDecoder errorDecoder() {
//        return new SearchErrorDecoder();
//    }

}