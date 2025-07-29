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
    // 유저의 정답을 불러오기 위한 VO

    private long answerId;
    private long checklistId;
    private long questionId;
    private long userId;
    private Boolean answer;
}