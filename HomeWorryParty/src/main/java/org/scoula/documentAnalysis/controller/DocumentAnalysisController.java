package org.scoula.documentAnalysis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.dangerResult.dto.DangerResultDTO;
import org.scoula.documentAnalysis.dto.DocumentAnalysisDTO;
import org.scoula.documentAnalysis.dto.DocumentAnalysisResultDTO;
import org.scoula.documentAnalysis.service.DocumentAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/analysis")
public class DocumentAnalysisController {

    final DocumentAnalysisService  documentAnalysisService;


    @GetMapping("")
    public ResponseEntity<DangerResultDTO>  getDangerResult(
            @RequestBody DocumentAnalysisDTO answerDTOList
    ){
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

}
