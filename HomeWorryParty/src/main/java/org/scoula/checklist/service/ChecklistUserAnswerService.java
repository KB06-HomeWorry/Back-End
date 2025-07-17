package org.scoula.checklist.service;
import org.scoula.checklist.domain.ChecklistUserAnswerVO;
import org.scoula.checklist.dto.ChecklistDTO;
import org.scoula.checklist.dto.ChecklistUserAnswerDTO;

import java.util.List;

public interface ChecklistUserAnswerService {
    List<ChecklistUserAnswerDTO> getAnswerList(Long templateId, long userId, List<ChecklistDTO> checklist);

    Long  makeAnswerMemory(Long checkListId, Long userId, Long templateId, List<ChecklistDTO> checklist);
    Long makeNewCheckListId(Long userId, Long templateId);

}