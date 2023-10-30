package com.ssafy.boardcollie.domain.chatbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.boardcollie.domain.chatbot.dto.QuestionRequestDto;
import com.ssafy.boardcollie.global.exception.GlobalRuntimeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class ChatBotServiceImpl implements ChatBotService {

    @Value("${openai.url}/chat/")
    private String API_ENDPOINT;
    @Value("${openai.key}")
    private String OPEN_AI_KEY;
    private final RestTemplate restTemplate;
    private final RedisService redisService;
    private static final String PROMPT_PREFIX = "보드게임 ";
    private static final String PROMPT_SUFFIX = "의 플레이 룰에 관한 질문이야: ";
    private static final String PROMPT_LANGUAGE = "부드러운 말투로 '해요'체로 대답해줘";

    public String getCompletion(QuestionRequestDto requestDto) {
        String prompt = requestDto.getPrompt();
        String gameName = requestDto.getGameName();
        String uuid = requestDto.getUuid();
        String url = API_ENDPOINT + "completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(OPEN_AI_KEY);

        Map<String, Object> requestBody = createBody();
        List<Map<String, Object>> messages = new ArrayList<>();
        Map<String, Object> userMessage = new HashMap<>();

        String prevPrompt = createPrevPrompt(redisService.getPrevPrompt(uuid), redisService.getPrevAnswer(uuid));
        messages.add(createSystemMap(prevPrompt));
        redisService.saveQuestionToRedis(prompt, uuid);


        messages.add(createSystemMap(PROMPT_LANGUAGE));
        userMessage.put("role", "user");
        userMessage.put("content", PROMPT_PREFIX + gameName + PROMPT_SUFFIX + prompt);
        messages.add(userMessage);
        requestBody.put("messages", messages);


        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request,
                    String.class);

            String answer = extractContent(response.getBody());
            redisService.saveAnswerToRedis(answer, uuid);
            return answer;

        } catch (Exception e) {
            log.info(e.getMessage());
            throw new GlobalRuntimeException("OpenAI 에러", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String createPrevPrompt(ArrayList<String> prevPrompts, ArrayList<String> prevAnswers) {
        StringBuilder prevPrompt = new StringBuilder();
        for(int i=0;i<prevPrompts.size();i++) {
            prevPrompt.append("user: 이전 질문 ").append(i).append(" ").append(prevPrompts.get(i))
                      .append("\n assistant: 답변 ").append(i).append(" ").append(prevAnswers.get(i)).append("\n");
        }
        return prevPrompt.toString();
    }

    private String extractContent(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode responseNode = objectMapper.readTree(responseBody);

            if (responseNode.has("choices") && responseNode.get("choices").isArray()) {
                JsonNode choice = responseNode.get("choices").get(0);
                if (choice.has("message") && choice.get("message").has("content")) {
                    return choice.get("message").get("content").asText();
                }
            }
            throw new GlobalRuntimeException("OpenAI 에러 : 답변을 생성하지 못함.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            throw new GlobalRuntimeException("OpenAI 에러 : 답변을 생성하지 못함.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @Override
    public String getUUID() {
        return UUID.randomUUID().toString();
    }

}
