package com.example.chatgptapi.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GptChatResponse {

    String id;

    String object;

    Long created;

    String model;

    List<Choice> choices;

    Usage usage;

}
