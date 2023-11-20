package com.ssafy.boardcollie.domain.chatbot.service;

import java.util.ArrayList;

public interface RedisService {

    public void saveQuestionToRedis(String prompt, String UUID);

    public void saveAnswerToRedis(String answer, String UUID);

    public ArrayList<String> getPrevPrompt(String UUID);

    public ArrayList<String> getPrevAnswer(String UUID);
}
