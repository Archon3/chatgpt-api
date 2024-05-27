package com.example.chatgptapi.service;

import com.example.chatgptapi.dto.*;

import java.util.ArrayList;
import java.util.List;

public interface GptService {

    default List<ChatMessage> chat(String prompt) {
        return this.chat(new ArrayList<>(), prompt);
    }

    List<ChatMessage> chat(List<ChatMessage> messages, String prompt);

}
