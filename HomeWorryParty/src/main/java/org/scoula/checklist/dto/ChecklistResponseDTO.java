package org.scoula.checklist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistResponseDTO {
    private List<ChecklistDTO> checklist;
    private List<ChecklistUserAnswerDTO> answers;
}