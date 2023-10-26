package com.ssafy.boardcollie.domain.chatbot.service;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService{

    private static final String QUEUE_QUESTION_KEY = "prevQuestion";
    private static final String QUEUE_ANSWER_KEY = "prevAnswer";
    private static final int QUEUE_SIZE = 3;
    private final RedisTemplate<String, String> redisTemplate;

    public void saveQuestionToRedis(String prompt) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        listOps.leftPush(QUEUE_QUESTION_KEY, prompt);
        if (listOps.size(QUEUE_QUESTION_KEY) > QUEUE_SIZE) {
            listOps.rightPop(QUEUE_QUESTION_KEY);
        }
    }

    @Override
    public void saveAnswerToRedis(String answer) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        listOps.leftPush(QUEUE_ANSWER_KEY, answer);
        if (listOps.size(QUEUE_ANSWER_KEY) > QUEUE_SIZE) {
            listOps.rightPop(QUEUE_ANSWER_KEY);
        }
    }

    public ArrayList<String> getPrevPrompt() {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        return new ArrayList<>(listOps.range(QUEUE_QUESTION_KEY, 0, -1));
    }

    @Override
    public ArrayList<String> getPrevAnswer() {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        return new ArrayList<>(listOps.range(QUEUE_ANSWER_KEY, 0, -1));
    }

}
