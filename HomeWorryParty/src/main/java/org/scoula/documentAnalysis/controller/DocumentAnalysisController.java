package org.scoula.documentAnalysis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.agent.domain.AgentDetailVO;
import org.scoula.dangerResult.dto.DangerResultDTO;
import org.scoula.documentAnalysis.dto.DocumentAgentDTO;
import org.scoula.documentAnalysis.dto.DocumentAnalysisDTO;
import org.scoula.documentAnalysis.dto.DocumentAnalysisResultDTO;
import org.scoula.documentAnalysis.service.DocumentAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/analysis")
public class DocumentAnalysisController {

    final DocumentAnalysisService documentAnalysisService;

    // 서류 분석 데이터를 받아서 서류 분석 결과를 돌려준다.
    @PostMapping("")
    public ResponseEntity<DangerResultDTO> getDangerResult(
            @RequestBody DocumentAnalysisDTO answerDTOList
    ) {
        log.info(answerDTOList);
        DocumentAnalysisResultDTO documentAnalysisResultDTO =
                documentAnalysisService.analysis(answerDTOList);

        DangerResultDTO dangerResultDTO = DangerResultDTO.builder()
                .grade(documentAnalysisResultDTO.getGrade())
                .message(documentAnalysisResultDTO.getMessage())
                .descriptionTitleList(documentAnalysisResultDTO.getDescriptionTitleList())
                .descriptionContentList(documentAnalysisResultDTO.getDescriptionContentList())
                .imageUrl(documentAnalysisResultDTO.getImageUrl())
                .build();

        return ResponseEntity.ok(dangerResultDTO);
    }

    // 사용자에게 받아서 해당 주소를 관리하는 중개사무소의 정보를 넘겨준다.
    @GetMapping("/agent/address")
    public ResponseEntity<List<AgentDetailVO>> getChargeAgentByAddress(
            @RequestParam String houseAddress
    ) {
        List<AgentDetailVO> agentDetailVOList = documentAnalysisService.checkDocumentAgentByAddress(houseAddress);

        return ResponseEntity.ok(agentDetailVOList);
    }
}
