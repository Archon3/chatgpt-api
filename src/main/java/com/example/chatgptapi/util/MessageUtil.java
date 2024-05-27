package com.example.chatgptapi.util;

import com.example.chatgptapi.dto.ChatMessage;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class MessageUtil {

    public static List<ChatMessage> getPrompt(String prompt) {
        ChatMessage message = ChatMessage.builder()
                .role("user")
                .content(prompt)
                .build();

        return List.of(message);
    }

    public static List<ChatMessage> getPrompt(List<ChatMessage> messages, String prompt) {
        messages.add(ChatMessage.builder()
                .role("user")
                .content(prompt)
                .build());

        return  messages;
    }
}
