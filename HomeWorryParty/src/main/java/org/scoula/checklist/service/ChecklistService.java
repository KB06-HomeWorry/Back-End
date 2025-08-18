package org.scoula.checklist.service;

import org.scoula.checklist.dto.ChecklistDTO;
import org.scoula.checklist.dto.ChecklistTemplateDTO;

import java.util.List;

public interface ChecklistService {


    List<ChecklistDTO> getChecklist(ChecklistTemplateDTO checklistTemplateDTO);

    List<ChecklistDTO> getChecklist(Long templateId);

    ChecklistTemplateDTO getChecklistTemplate(String stage, String type);


}
