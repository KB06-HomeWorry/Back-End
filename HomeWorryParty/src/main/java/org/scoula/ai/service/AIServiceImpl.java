package org.scoula.ai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;

@Service
public class AIServiceImpl {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    private static final String SYSTEM_PROMPT = """
당신은 부동산 전문 용어를 쉽게 풀어 설명해주는 한국어 문장 리라이팅 도우미입니다.
사용자가 입력한 문장에 어려운 표현이나 부동산 용어가 포함되어 있다면, 전문 지식이 없어도 이해할 수 있도록 더 쉽고 명확한 문장으로 바꿔주세요.
반드시 한국어로 대답하세요.
""";

    public String simplifySentence(String input) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> message1 = Map.of("role", "system", "content", SYSTEM_PROMPT);
        Map<String, Object> message2 = Map.of("role", "user", "content", input);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", List.of(message1, message2));
        requestBody.put("max_tokens", 300);
        requestBody.put("temperature", 0.5);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
        if (choices != null && !choices.isEmpty()) {
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return (String) message.get("content");
        }
        throw new RuntimeException("No response from OpenAI");
    }
}
