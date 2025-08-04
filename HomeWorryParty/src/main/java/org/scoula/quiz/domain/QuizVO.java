package org.scoula.quiz.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizVO {
    private Long quizId;
    private String type;
    private String question;
    private String level;
    private String choices;
    private String explanation;
    private String correctAnswer;
}
