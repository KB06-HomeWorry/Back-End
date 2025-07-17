package org.scoula.checklist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.checklist.dto.ChecklistDTO;
import org.scoula.checklist.dto.ChecklistResponseDTO;
import org.scoula.checklist.dto.ChecklistTemplateDTO;
import org.scoula.checklist.dto.ChecklistUserAnswerDTO;
import org.scoula.checklist.service.ChecklistService;
import org.scoula.checklist.service.ChecklistUserAnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

        ChecklistTemplateDTO templateDTO = questionService.getChecklistTemplate(type, stage);
        List<ChecklistDTO> checklist = questionService.getChecklist(templateDTO);



        List<ChecklistUserAnswerDTO> answers =
                answerService.getAnswerList(templateDTO.getTemplateId(), user_id, checklist);


        ChecklistResponseDTO response = ChecklistResponseDTO.builder()
                .checklist(checklist)
                .answers(answers)
                .build();


        log.info(answers);

        return ResponseEntity.ok(response);
    }

}