package com.ssafy.boardcollie.domain.chatbot.service;

import com.ssafy.boardcollie.global.exception.GlobalRuntimeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAiService {

    @Value("${openai.url}/chat/")
    private String API_ENDPOINT;
    @Value("${openai.key}")
    private String OPEN_AI_KEY;
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String QUEUE_KEY = "prevQuestion";
    private static final int QUEUE_SIZE = 3;
    private static final String PROMPT_PREFIX = "보드게임 ";
    private static final String PROMPT_SUFFIX = "의 플레이 룰에 관한 질문이야: ";
    private static final String PROMPT_LANGUAGE = "부드러운 말투로 '해요'체로 대답해줘";

    public String getCompletion(String prompt) {
        String url = API_ENDPOINT + "completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(OPEN_AI_KEY);

        Map<String, Object> requestBody = createBody();
        List<Map<String, Object>> messages = new ArrayList<>();
        Map<String, Object> userMessage = new HashMap<>();

        ArrayList<String> prevPrompt = getPrevPrompt();
        for (String s : prevPrompt) {
            messages.add(createSystemMap("이전 질문이야: " + s));
        }
        saveToRedis(prompt);
        String gameName = "루미큐브";

        messages.add(createSystemMap(PROMPT_LANGUAGE));
        userMessage.put("role", "user");
        userMessage.put("content", PROMPT_PREFIX + gameName + PROMPT_SUFFIX + prompt);
        messages.add(userMessage);
        requestBody.put("messages", messages);


        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request,
                    String.class);
            return response.getBody();
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new GlobalRuntimeException("OpenAI 에러", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void saveToRedis(String prompt) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        listOps.leftPush(QUEUE_KEY, prompt);
        if (listOps.size(QUEUE_KEY) > QUEUE_SIZE) {
            listOps.rightPop(QUEUE_KEY);
        }
    }

    private ArrayList<String> getPrevPrompt() {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        return new ArrayList<>(listOps.range(QUEUE_KEY, 0, -1));
    }

    private Map<String, Object> createSystemMap(String content) {
        Map<String, Object> system = new HashMap<>();
        system.put("role", "system");
        system.put("content", content);
        return system;
    }

    private Map<String, Object> createBody() {

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("model", "gpt-4");
        requestBody.put("temperature", 0.8);
        requestBody.put("max_tokens", 1024);
        requestBody.put("top_p", 1);
        requestBody.put("frequency_penalty", 0.5);
        requestBody.put("presence_penalty", 0.5);

        return requestBody;
    }

}
