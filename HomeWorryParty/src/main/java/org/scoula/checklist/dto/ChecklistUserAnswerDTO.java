package org.scoula.checklist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.checklist.domain.ChecklistUserAnswerVO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistUserAnswerDTO {
    private long answerId;
    private long checklistId;
    private long questionId;
    private long userId;
    private Boolean answer;

    public static ChecklistUserAnswerDTO of(ChecklistUserAnswerVO vo) {
        return ChecklistUserAnswerDTO.builder()
                .answerId(vo.getAnswerId())
                .checklistId(vo.getChecklistId())
                .questionId(vo.getQuestionId())
                .userId(vo.getUserId())
                .answer(vo.getAnswer())
                .build();
    }
}