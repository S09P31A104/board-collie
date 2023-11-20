package com.ssafy.boardcollie.domain.chatbot.service;

import com.ssafy.boardcollie.domain.chatbot.dto.QuestionRequestDto;

public interface ChatBotService {

    public String getCompletion(QuestionRequestDto requestDto);

    public String getUUID();

    public String createQR(Long gameId);

}
