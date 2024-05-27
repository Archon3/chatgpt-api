package com.example.chatgptapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Choice {

    ChatMessage message;

    @JsonProperty("finish_reason")
    private String finishReason;

    private int index;
}
