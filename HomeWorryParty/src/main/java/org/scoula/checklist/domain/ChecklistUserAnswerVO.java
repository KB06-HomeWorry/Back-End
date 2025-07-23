package org.scoula.checklist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistUserAnswerVO {
    private long answerId;
    private long checklistId;
    private long questionId;
    private long userId;
    private Boolean answer;
}