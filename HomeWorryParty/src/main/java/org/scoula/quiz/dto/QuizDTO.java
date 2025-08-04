package org.scoula.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.quiz.domain.QuizVO;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizDTO {
    private Long number;
    private String type;
    private String question;
    private String level;
    private List<String> choices;
    private String explanation;
    private String correctAnswer;

    public QuizDTO of(QuizVO vo) {
        return QuizDTO.builder()
                .number(vo.getQuizId())
                .type(vo.getType())
                .question(vo.getQuestion())
                .level(vo.getLevel())
                .choices(vo.getChoices().isEmpty() ? new ArrayList<>() : List.of(vo.getChoices().split(",")))
                .explanation(vo.getExplanation())
                .correctAnswer(vo.getCorrectAnswer())
                .build();
    }
}
