package com.example.chatgptapi.service;


import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class GptV4ServiceTest {

    @Autowired
    ChatgptService chatgptService;
    /*
       org.springframework.ai:spring-ai-openai-spring-boot-starter
       FeignClient 설정이 있는 경우 bean 초기화 되지 않음 - GptV3과 동시에 사용하지 못함.
       [org.springframework.ai.openai.OpenAiChatClient]
    */

    @Test
    void chat_test() {
        //given
        String prompt = "너 이름은 뭐야?";

        //when
        String message = chatgptService.sendMessage(prompt);

        //then
        log.info("message:{}", message);
    }

}