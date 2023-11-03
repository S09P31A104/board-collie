package com.ssafy.boardcollie.domain.chatbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.ssafy.boardcollie.domain.chatbot.dto.QuestionRequestDto;
import com.ssafy.boardcollie.domain.chatbot.entity.Question;
import com.ssafy.boardcollie.domain.chatbot.repository.QuestionRepository;
import com.ssafy.boardcollie.domain.game.entity.Game;
import com.ssafy.boardcollie.domain.game.repository.GameRepository;
import com.ssafy.boardcollie.global.aws.S3Uploader;
import com.ssafy.boardcollie.global.exception.GlobalRuntimeException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;
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
    private final S3Uploader s3Uploader;
    private final QuestionRepository questionRepository;
    private final GameRepository gameRepository;
    private static final String PROMPT_PREFIX = "보드게임 ";
    private static final String PROMPT_SUFFIX = "의 플레이 룰에 관한 질문이야: ";
    private static final String PROMPT_LANGUAGE = "부드러운 말투로 '해요'체로 대답해줘.";
    private static final String PROMPT_PREV_QUESTION = "이전 질문 데이터가 입력되면 이전 질문도 고려하여 답변해줘. 룰북을 확인하라는 말은 하면 안돼.";

    public String getCompletion(QuestionRequestDto requestDto) {
        String prompt = requestDto.getPrompt();
        // 추후 게임 API와 연동 예정
        Game game = gameRepository.findById(requestDto.getGameId())
                                  .orElseThrow(() -> new GlobalRuntimeException("해당하는 게임이 없습니다.", HttpStatus.NOT_FOUND));
        String gameName = game.getGameTitleEng();
        String uuid = requestDto.getUuid();
        String url = API_ENDPOINT + "completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(OPEN_AI_KEY);

        Map<String, Object> requestBody = createBody();
        List<Map<String, Object>> messages = new ArrayList<>();
        Map<String, Object> userMessage = new HashMap<>();

        String prevPrompt = createPrevPrompt(redisService.getPrevPrompt(uuid),
                redisService.getPrevAnswer(uuid));
        messages.add(createSystemMap(prevPrompt));
        log.info(prevPrompt);
        redisService.saveQuestionToRedis(prompt, uuid);

        messages.add(createSystemMap(PROMPT_LANGUAGE));
        messages.add(createSystemMap(PROMPT_PREV_QUESTION));
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

            saveQuestion(Question.createQuestion(requestDto.getPrompt(),
                    requestDto.getGameId(), answer));

            return answer;

        } catch (Exception e) {
            log.info(e.getMessage());
            throw new GlobalRuntimeException("OpenAI 에러", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String createPrevPrompt(ArrayList<String> prevPrompts, ArrayList<String> prevAnswers) {
        StringBuilder prevPrompt = new StringBuilder();
        for (int i = 0; i < prevPrompts.size(); i++) {
            prevPrompt.append("user: 이전 질문 ").append(i).append(" ").append(prevPrompts.get(i))
                      .append("\n assistant: 답변 ").append(i).append(" ").append(prevAnswers.get(i))
                      .append("\n");
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
            throw new GlobalRuntimeException("OpenAI 에러 : 답변을 생성하지 못함.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            throw new GlobalRuntimeException("OpenAI 에러 : 답변을 생성하지 못함.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
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

    @Override
    public String createQR(Long gameId) {
        int width = 200;
        int height = 200;
        String url = "http://www.boardcollie.com/chatbot/" + gameId;
        String directory = "chatbot/qr/";
        String prefix = "QR";
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            File file = new File(prefix + gameId + ".jpg");
            String fileName = directory + prefix + gameId + ".jpg";
            ImageIO.write(bufferedImage, "jpg", file);

            return saveQR(file, fileName);
        } catch (Exception e) {
            throw new GlobalRuntimeException("QR 생성 오류", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String saveQR(File qrCode, String fileName) {
        return s3Uploader.upload(fileName, qrCode);
    }

    private void saveQuestion(Question question) {
        questionRepository.save(question);
    }

}
