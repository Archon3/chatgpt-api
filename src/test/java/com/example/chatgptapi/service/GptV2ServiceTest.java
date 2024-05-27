package com.example.chatgptapi.service;

import com.example.chatgptapi.dto.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class GptV2ServiceTest {

    @Autowired
    GptV2Service gptService;

    @Test
    void chat_test_v1() {
        //given
        String prompt = "1+2=?";

        //when
        List<ChatMessage> messages = gptService.chat(prompt);

        //then
        System.out.println("**************************************");

        for (ChatMessage message : messages) {
            log.info("message: {}", message);
        }
    }

    @Test
    void chat_test_v2() {
        //given
        String prompt1 = "1+2=?";
        String prompt2 = "너가 답해준 값에 3을 곱해줘";

        //when
        List<ChatMessage> messages = gptService.chat(prompt1);
        List<ChatMessage> resultList = gptService.chat(messages, prompt2);

        //then
        System.out.println("**************************************");
        for (ChatMessage message : resultList) {
            log.info("message - role: {}, content: {}", message.getRole(), message.getContent());
        }
    }

}