package com.example.chatgptapi.feign;

import com.example.chatgptapi.dto.GptChatRequest;
import com.example.chatgptapi.dto.GptChatResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "gpt-client", url = "https://api.openai.com", configuration = GptFeignClientConfig.class)
public interface GptFeignClient {

    @PostMapping(value = "/v1/chat/completions")
    GptChatResponse chat(GptChatRequest request);

    @PostMapping(value = "/v1/chat/completions")
    ResponseEntity<String> chatToString(GptChatRequest request);

}
