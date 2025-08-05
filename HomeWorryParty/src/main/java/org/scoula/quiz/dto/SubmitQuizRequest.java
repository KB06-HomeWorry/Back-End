package org.scoula.quiz.dto;

import lombok.Data;

@Data
public class SubmitQuizRequest {
    private Long userId;
    private Long quizId;
    private String answer;
    private boolean correct;
}