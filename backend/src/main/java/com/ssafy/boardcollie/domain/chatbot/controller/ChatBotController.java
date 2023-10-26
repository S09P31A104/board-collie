package com.ssafy.boardcollie.domain.chatbot.controller;

import com.ssafy.boardcollie.domain.chatbot.dto.QuestionRequestDto;
import com.ssafy.boardcollie.domain.chatbot.service.OpenAIService;
import com.ssafy.boardcollie.domain.chatbot.service.OpenAiServiceImpl;
import com.ssafy.boardcollie.global.response.JsonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/s1/chatbot")
@RequiredArgsConstructor
public class ChatBotController {

    private final OpenAIService openAIService;

    @PostMapping("/question")
    public ResponseEntity<?> question(@RequestBody QuestionRequestDto requestDto) {
        return JsonResponse.ok("챗봇 질문하기 완료", openAIService.getCompletion(requestDto.getPrompt()));
    }

}
