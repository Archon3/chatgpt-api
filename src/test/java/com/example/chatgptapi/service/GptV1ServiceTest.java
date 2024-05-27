package com.example.chatgptapi.service;

import com.example.chatgptapi.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class GptV1ServiceTest {

    @Autowired
    GptV1Service gptService;
    /*
        /v1/chat/completions API:
        챗봇, 고객 서비스 자동화, 인터랙티브 게임 등 대화형 서비스에 주로 사용
        role (system, user, assistant)
     */

    @Test
    void chat_test_v1() {
        //given
        String prompt = "1+2=?";

        //when
        GptChatResponse response = gptService._chat(prompt);

        //then
        System.out.println("**************************************");
        log.info("response: {}", response);

        for (Choice choice : response.getChoices()) {
            log.info("message: {}", choice.getMessage());
        }
    }

    @Test
    void chat_test_v2() {
        //https://platform.openai.com/docs/api-reference/chat/create
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