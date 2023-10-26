package com.ssafy.boardcollie.domain.chatbot.service;

import java.util.ArrayList;

public interface RedisService {

    public void saveQuestionToRedis(String prompt);

    public void saveAnswerToRedis(String answer);

    public ArrayList<String> getPrevPrompt();

    public ArrayList<String> getPrevAnswer();
}
