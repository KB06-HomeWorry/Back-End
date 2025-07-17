package org.scoula.checklist.service;
import org.scoula.checklist.dto.ChecklistUserAnswerDTO;

import java.util.List;

public interface ChecklistUserAnswerService {
    List<ChecklistUserAnswerDTO> getAnswerList(long checklistId, long userId);
}