package org.scoula.documentAnalysis.service;

import org.scoula.documentAnalysis.dto.DocumentAnalysisDTO;
import org.scoula.documentAnalysis.dto.DocumentAnalysisResultDTO;
import org.scoula.documentAnalysis.dto.IllegalBuildingCheckDTO;

public interface DocumentAnalysisService {
    void insertIllegalBuildingData(IllegalBuildingCheckDTO illegalBuildingCheckDTO);

    DocumentAnalysisResultDTO analysis(DocumentAnalysisDTO answerDTOList);
}
