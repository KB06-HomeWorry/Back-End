package org.scoula.checklist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.checklist.domain.ChecklistTemplateVO;
import org.scoula.checklist.domain.ChecklistVO;
import org.scoula.checklist.dto.ChecklistDTO;
import org.scoula.checklist.dto.ChecklistTemplateDTO;
import org.scoula.checklist.mapper.ChecklistMapper;
import org.scoula.checklist.mapper.ChecklistTemplateMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ChecklistServiceImpl implements ChecklistService {

    final ChecklistMapper checklistMapper;
    final ChecklistTemplateMapper checklistTemplateMapper;


    @Override
    public List<ChecklistDTO> getChecklist(ChecklistTemplateDTO checklistTemplateDTO) {

        long templateId = checklistTemplateDTO.getTemplateId();

        List<ChecklistVO> checklistVOList = Optional
                .ofNullable(checklistMapper.get(templateId))
                .orElseThrow(NoSuchElementException::new);

        List<ChecklistDTO> checklistDTOList = checklistVOList.stream()
                .map(ChecklistDTO::of)
                .collect(Collectors.toList());

        return checklistDTOList;
    }

    @Override
    public ChecklistTemplateDTO getChecklistTemplate(String type, String stage) {
        ChecklistTemplateVO templateVO = Optional
                .ofNullable(checklistTemplateMapper.findByTypeAndStage(type, stage))
                .orElseThrow(NoSuchElementException::new);

        return ChecklistTemplateDTO.of(templateVO);
    }


}
