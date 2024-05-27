package com.example.chatgptapi.restClient;

import com.example.chatgptapi.dto.GptChatRequest;
import com.example.chatgptapi.dto.GptChatResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface GptRestClient {

    @PostExchange("/v1/chat/completions")
    GptChatResponse chat(@RequestBody GptChatRequest request);

}
