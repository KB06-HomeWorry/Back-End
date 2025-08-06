package org.scoula.quiz.service;

import org.scoula.quiz.domain.UserQuizStatusVO;

import java.util.List;

public interface UserQuizStatusService {
    void submitQuiz(Long userId, Long quizId, String answer, boolean isCorrect);

    List<UserQuizStatusVO> getUserStatus(Long userId);

    List<UserQuizStatusVO> getCompletedQuizzes(Long userId);
}