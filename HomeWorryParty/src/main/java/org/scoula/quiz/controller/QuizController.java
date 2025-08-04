package org.scoula.quiz.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.quiz.dto.QuizDTO;
import org.scoula.quiz.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequestMapping("/api/quiz")
@RestController
@RequiredArgsConstructor
public class QuizController {
    private final QuizService service;

    @GetMapping("getQuiz") // 퀴즈 목록 조회
    public ResponseEntity<List<QuizDTO>> getQuiz() {
        return ResponseEntity.ok().body(service.getQuiz());
    }
}
