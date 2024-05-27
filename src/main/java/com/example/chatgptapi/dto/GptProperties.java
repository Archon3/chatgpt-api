package com.example.chatgptapi.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "chatgpt")
public class GptProperties {

    private String apiKey = "";

    private String baseUrl = "https://api.openai.com";

    private String chatUri = "/v1/chat/completions";

    private String model = "gpt-3.5-turbo";

    private Double temperature = 0.0;

}