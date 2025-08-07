package org.scoula.ai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;
import java.util.regex.Pattern;

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

    public String explainRiskClause(String details) {
        RestTemplate restTemplate = new RestTemplate();

        String prompt = """
당신은 부동산 계약서 위험 조항 분석 전문가입니다.
다음 위험 조항 내용을 보고,
- 위험 조항 제목(title)
- AI 권장 조치(recommendation)
두 가지 정보를 JSON 객체 형식으로 아래 예시처럼 명확하게 답변해 주세요.

예시:
{
  "title": "중도금 미지급 위험",
  "recommendation": "중도금 지급 조건을 명확히 하고, 미지급시 계약 해지 가능 조항을 포함하세요."
}

위험 조항 내용:
""" + details;

        Map<String, Object> message1 = Map.of("role", "system", "content", "당신은 부동산 계약서 위험 조항 분석 전문가입니다.");
        Map<String, Object> message2 = Map.of("role", "user", "content", prompt);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", List.of(message1, message2));
        requestBody.put("max_tokens", 300);
        requestBody.put("temperature", 0.3);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);
            Map body = response.getBody();
            if (body == null) throw new RuntimeException("OpenAI 응답이 비어있습니다.");

            List<Map<String, Object>> choices = (List<Map<String, Object>>) body.get("choices");
            if (choices == null || choices.isEmpty()) throw new RuntimeException("OpenAI 응답에 choices 항목이 없습니다.");

            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            if (message == null || !message.containsKey("content")) {
                throw new RuntimeException("OpenAI 응답에 message 또는 content 항목이 없습니다.");
            }

            String content = (String) message.get("content");

            // 백틱 제거
            content = content.replaceAll("(?s)```(json)?", "").replaceAll("```", "").trim();

            // 정규식으로 JSON 블록만 추출
            var matcher = Pattern.compile("\\{.*?\\}", Pattern.DOTALL).matcher(content);
            if (!matcher.find()) {
                return fallbackJson("AI 응답이 명확하지 않음", "문장을 명확하게 작성해서 다시 시도해 주세요.");
            }

            String jsonBlock = matcher.group();

            // JSON 유효성 검사
            new ObjectMapper().readTree(jsonBlock);

            return jsonBlock;

        } catch (Exception e) {
            return fallbackJson("AI 분석 실패", "AI 분석 중 오류가 발생했습니다. 입력을 다시 확인해 주세요.");
        }
    }

    private String fallbackJson(String title, String recommendation) {
        return String.format("""
        {
            "title": "%s",
            "recommendation": "%s"
        }
        """, title, recommendation);
    }
}