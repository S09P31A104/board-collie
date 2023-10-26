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

    public void saveQuestionToRedis(String prompt, String UUID) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        listOps.leftPush(QUEUE_QUESTION_KEY + UUID, prompt);
        if (listOps.size(QUEUE_QUESTION_KEY + UUID) > QUEUE_SIZE) {
            listOps.rightPop(QUEUE_QUESTION_KEY);
        }
    }

    @Override
    public void saveAnswerToRedis(String answer, String UUID) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        listOps.leftPush(QUEUE_ANSWER_KEY + UUID, answer);
        if (listOps.size(QUEUE_ANSWER_KEY + UUID) > QUEUE_SIZE) {
            listOps.rightPop(QUEUE_ANSWER_KEY);
        }
    }

    public ArrayList<String> getPrevPrompt(String UUID) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        return new ArrayList<>(listOps.range(QUEUE_QUESTION_KEY + UUID, 0, -1));
    }

    @Override
    public ArrayList<String> getPrevAnswer(String UUID) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        return new ArrayList<>(listOps.range(QUEUE_ANSWER_KEY + UUID, 0, -1));
    }

}
