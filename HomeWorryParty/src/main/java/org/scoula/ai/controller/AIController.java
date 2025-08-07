package org.scoula.ai.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.scoula.ai.service.AIServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/ai")
public class AIController {

    private static final Logger log = LoggerFactory.getLogger(AIController.class);
    private final AIServiceImpl aiService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AIController(AIServiceImpl aiService) {
        this.aiService = aiService;
    }

    // 부동산 용어 해석 API
    @PostMapping("/estate-ease")
    public ResponseEntity<?> simplify(@RequestBody String input) {
        log.info("부동산 용어 입력값: {}", input);

        if (input == null || input.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("입력값이 비어있습니다.");
        }

        try {
            String result = aiService.simplifySentence(input.trim());
            return ResponseEntity.ok(Map.of("result", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("처리 중 오류가 발생했습니다.");
        }
    }

    // 위험 계약서 조항 분석 API
    @PostMapping("/analysis")
    public ResponseEntity<?> analyzeRiskClause(@RequestBody Map<String, String> request) {
        String details = request.get("details");

        log.info("위험 조항 내용: {}", details);

        if (details == null || details.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("위험 조항 내용이 비어있습니다.");
        }

        try {
            String jsonResult = aiService.explainRiskClause(details.trim());
            Map<String, Object> resultMap = objectMapper.readValue(jsonResult, Map.class);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("분석 중 오류가 발생했습니다.");
        }
    }
}