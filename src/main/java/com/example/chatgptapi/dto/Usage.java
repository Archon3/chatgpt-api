package com.example.chatgptapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usage {

    @JsonProperty("prompt_tokens")
    int promptTokens;

    @JsonProperty("completion_tokens")
    int completionTokens;

    @JsonProperty("total_tokens")
    int totalTokens;
}
