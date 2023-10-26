package com.ssafy.boardcollie.domain.chatbot.service;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private static final String QUEUE_KEY = "prevQuestion";
    private static final int QUEUE_SIZE = 3;
    private static final String indexKey = "indexKey";

    private final RedisTemplate<String, String> redisTemplate;

    public void saveToRedis(String prompt) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        listOps.leftPush(QUEUE_KEY, prompt);
        if (listOps.size(QUEUE_KEY) > QUEUE_SIZE) {
            listOps.rightPop(QUEUE_KEY);
        }
    }

    public ArrayList<String> getPrevPrompt() {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        return new ArrayList<>(listOps.range(QUEUE_KEY, 0, -1));
    }

}
