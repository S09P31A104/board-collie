package com.ssafy.boardcollie.domain.chatbot.controller;

import com.ssafy.boardcollie.domain.chatbot.dto.QuestionRequestDto;
import com.ssafy.boardcollie.domain.chatbot.service.OpenAiService;
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

    private final OpenAiService openAiService;

    @PostMapping("/question")
    public ResponseEntity<?> question(@RequestBody QuestionRequestDto requestDto) {
        return ResponseEntity.ok().body(openAiService.getCompletion(requestDto.getPrompt()));
    }
}
