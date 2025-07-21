package org.scoula.dangerResult.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.checklist.dto.ChecklistDTO;
import org.scoula.checklist.dto.ChecklistResponseDTO;
import org.scoula.checklist.dto.ChecklistTemplateDTO;
import org.scoula.checklist.dto.ChecklistUserAnswerDTO;
import org.scoula.checklist.service.ChecklistService;
import org.scoula.dangerResult.domain.DangerResultVO;
import org.scoula.dangerResult.dto.DangerResultDTO;
import org.scoula.dangerResult.service.DangerResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/dangerResult")
public class DangerResultController {
    final DangerResultService dangerResultService;
    final ChecklistService checklistService;

    @PostMapping("")
    public ResponseEntity<ChecklistResponseDTO> getChecklist(
            @RequestParam String type,
            @RequestParam String stage,
            @RequestParam Long user_id,
            @RequestBody List<ChecklistUserAnswerDTO> answers
    ) {
        return null;
    }

    @GetMapping("")
    public ResponseEntity<DangerResultDTO>  getDangerResult(
            @RequestParam String type,
            @RequestParam String stage,
            @RequestParam Long user_id
    ){
        log.info("getDangerResult");
        Long templateId = checklistService.getChecklistTemplate(type, stage).getTemplateId();
        DangerResultVO dangerResultVO = dangerResultService.analysisDangerResult(templateId, user_id);

        DangerResultDTO dangerResultDTO = DangerResultDTO.builder()
                .grade(dangerResultVO.getGrade())
                .templateId(dangerResultVO.getTemplateId())
                .minScore(dangerResultVO.getMinScore())
                .maxScore(dangerResultVO.getMaxScore())
                .message(dangerResultVO.getMessage())
                .description(dangerResultVO.getDescription())
                .imageUrl(dangerResultVO.getImageUrl())
                .build();

        return ResponseEntity.ok(dangerResultDTO);
    }

}
