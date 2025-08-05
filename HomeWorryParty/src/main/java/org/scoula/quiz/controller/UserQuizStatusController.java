package org.scoula.quiz.controller;

import lombok.RequiredArgsConstructor;
import org.scoula.quiz.domain.UserQuizStatusVO;
import org.scoula.quiz.dto.SubmitQuizRequest;
import org.scoula.quiz.service.UserQuizStatusService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz/user")
@RequiredArgsConstructor
public class UserQuizStatusController {
    private final UserQuizStatusService userQuizStatusService;

    @PostMapping("/submit")
    public void submitQuiz(@RequestBody SubmitQuizRequest req) {
        userQuizStatusService.submitQuiz(req.getUserId(), req.getQuizId(), req.getAnswer(), req.isCorrect());
    }

    @GetMapping("/{userId}")
    public List<UserQuizStatusVO> getUserStatus(@PathVariable Long userId) {
        return userQuizStatusService.getUserStatus(userId);
    }

    @GetMapping("/{userId}/{quizId}")
    public UserQuizStatusVO getQuizStatus(@PathVariable Long userId, @PathVariable Long quizId) {
        return userQuizStatusService.getQuizStatus(userId, quizId);
    }
}