package com.example.chatgptapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GptChatRequest {

    @Builder.Default
    String model="gpt-3.5-turbo";

    @JsonProperty("max_tokens")
    @Builder.Default
    Integer maxTokens=300;

    @Builder.Default
    Double temperature=0.0;

    Boolean stream;

    List<ChatMessage> messages;


}
