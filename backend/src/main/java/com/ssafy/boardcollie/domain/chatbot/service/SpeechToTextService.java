package com.ssafy.boardcollie.domain.chatbot.service;

import com.ssafy.boardcollie.domain.chatbot.dto.SpeechToTextRequestDto;
import com.ssafy.boardcollie.domain.chatbot.dto.SpeechToTextResponseDto;

public interface SpeechToTextService {
    SpeechToTextResponseDto convertSpeechToText(SpeechToTextRequestDto requestDto);
}
