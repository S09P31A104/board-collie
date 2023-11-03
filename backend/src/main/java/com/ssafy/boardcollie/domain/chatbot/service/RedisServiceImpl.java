package com.ssafy.boardcollie.domain.chatbot.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
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
        String key = QUEUE_QUESTION_KEY + UUID;
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        listOps.leftPush(key, prompt);
        Long size = listOps.size(key);
        if (size != null && size > QUEUE_SIZE) {
            listOps.rightPop(key);
        }
        redisTemplate.expire(key, 60, TimeUnit.MINUTES);
    }

    @Override
    public void saveAnswerToRedis(String answer, String UUID) {
        String key = QUEUE_ANSWER_KEY + UUID;
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        listOps.leftPush(key, answer);
        Long size = listOps.size(key);
        if (size != null && size > QUEUE_SIZE) {
            listOps.rightPop(key);
        }
        redisTemplate.expire(key, 60, TimeUnit.MINUTES);
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
