package org.scoula.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQuizStatusVO {
    private Long userId;
    private Long quizId;
    private Boolean isSolved;
    private Boolean isCorrect;
    private String answer;
    private LocalDateTime solvedAt;
}