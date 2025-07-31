package org.scoula.ai.controller;

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

    private final AIServiceImpl AIServiceImpl;

    public AIController(AIServiceImpl AIServiceImpl) {
        this.AIServiceImpl = AIServiceImpl;
    }

    @PostMapping("/estate-ease")
    public ResponseEntity<?> simplify(@RequestBody String input) {
        log.info("부동산 용어 입력값: '{}'", input);

        if (input == null || input.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("입력값이 비어있습니다.");
        }
        try {
            String result = AIServiceImpl.simplifySentence(input.trim());
            return ResponseEntity.ok(Map.of("result", result));
        } catch (Exception e) {
            log.error("에러 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("처리 중 오류가 발생했습니다.");
        }
    }
}