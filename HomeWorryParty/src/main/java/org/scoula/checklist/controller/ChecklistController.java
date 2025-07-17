package org.scoula.checklist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.checklist.dto.ChecklistDTO;
import org.scoula.checklist.dto.ChecklistResponseDTO;
import org.scoula.checklist.dto.ChecklistUserAnswerDTO;
import org.scoula.checklist.service.ChecklistService;
import org.scoula.checklist.service.ChecklistUserAnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/checklist")
public class ChecklistController {

    final ChecklistService questionService;
    final ChecklistUserAnswerService answerService;

    @GetMapping("")
    public ResponseEntity<ChecklistResponseDTO> getChecklist(
            @RequestParam String type,
            @RequestParam String stage,
            @RequestParam Long user_id
    ) {
        log.info("getChecklist 요청됨: type={}, stage={}, user_id={}", type, stage, user_id);

        List<ChecklistDTO> checklist = questionService.getChecklist(type, stage);

        Long checklistId = null;
        if (!checklist.isEmpty()) {
            checklistId = checklist.get(0).getChecklistId();
        }

        List<ChecklistUserAnswerDTO> answers = checklistId == null
                ? List.of()
                : answerService.getAnswerList(checklistId, user_id);

        ChecklistResponseDTO response = ChecklistResponseDTO.builder()
                .checklist(checklist)
                .answers(answers)
                .build();

        return ResponseEntity.ok(response);
    }
}