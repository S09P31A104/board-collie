package com.ssafy.boardcollie.domain.chatbot.controller;

import com.ssafy.boardcollie.domain.chatbot.dto.QuestionRequestDto;
import com.ssafy.boardcollie.domain.chatbot.service.ChatBotService;
import com.ssafy.boardcollie.global.response.JsonResponse;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/s1/chatbot")
@RequiredArgsConstructor
public class ChatBotController {

    private final ChatBotService chatBotService;

    @PostMapping("/question")
    public ResponseEntity<?> question(@RequestBody QuestionRequestDto requestDto) {
        return JsonResponse.ok("챗봇 질문하기 완료", chatBotService.getCompletion(requestDto));
    }

    @GetMapping("/id")
    public ResponseEntity<?> createID() {
        return JsonResponse.ok("챗봇 UUID 생성 완료", chatBotService.getUUID());
    }

    @GetMapping("/QR")
    public ResponseEntity<?> createQR(@RequestParam("gameId") @NotNull Long gameId) {
        return JsonResponse.ok("챗봇 QR 생성 완료", chatBotService.createQR(gameId));
    }

}
