package com.ssafy.boardcollie.domain.chatbot.controller;

import com.ssafy.boardcollie.domain.chatbot.dto.SpeechToTextRequestDto;
import com.ssafy.boardcollie.domain.chatbot.dto.SpeechToTextResponseDto;
import com.ssafy.boardcollie.domain.chatbot.service.SpeechToTextService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/s1/chatbot/stt")
@CrossOrigin("*")
public class SpeechToTextController {
    private final SpeechToTextService speechToTextService;

    @PostMapping("/speech-to-text")
    public SpeechToTextResponseDto convertSpeechToText(@RequestBody SpeechToTextRequestDto requestDto) {
        return speechToTextService.convertSpeechToText(requestDto);
    }
}
