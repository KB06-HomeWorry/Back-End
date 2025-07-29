package org.scoula.dangerResult.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.checklist.service.ChecklistService;
import org.scoula.dangerResult.domain.DangerResultVO;
import org.scoula.dangerResult.dto.DangerResultDTO;
import org.scoula.dangerResult.service.DangerResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/dangerResult")
public class DangerResultController {
    final DangerResultService dangerResultService;
    final ChecklistService checklistService;

    // 타입과 단계, 유저 정보를 받아서 분석 후 체크리스트 위험도 결과를 front에 반환하는 객체
    @GetMapping("")
    public ResponseEntity<DangerResultDTO> getDangerResult(
            @RequestParam String type,
            @RequestParam String stage,
            @RequestParam Long user_id
    ) {
        log.info("getDangerResult");
        Long templateId = checklistService.getChecklistTemplate(type, stage).getTemplateId();
        DangerResultVO dangerResultVO = dangerResultService.analysisDangerResult(templateId, user_id);

        DangerResultDTO dangerResultDTO = DangerResultDTO.builder()
                .grade(dangerResultVO.getGrade())
                .templateId(dangerResultVO.getTemplateId())
                .minScore(dangerResultVO.getMinScore())
                .maxScore(dangerResultVO.getMaxScore())
                .message(dangerResultVO.getMessage())
                .descriptionTitleList(dangerResultVO.getDescriptionTitleList())
                .descriptionContentList(dangerResultVO.getDescriptionContentList())
                .imageUrl(dangerResultVO.getImageUrl())
                .build();

        return ResponseEntity.ok(dangerResultDTO);
    }
}
