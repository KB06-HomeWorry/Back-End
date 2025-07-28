package org.scoula.documentAnalysis.service;

import org.scoula.agent.domain.AgentDetailVO;
import org.scoula.documentAnalysis.dto.DocumentAgentDTO;
import org.scoula.documentAnalysis.dto.DocumentAnalysisDTO;
import org.scoula.documentAnalysis.dto.DocumentAnalysisResultDTO;
import org.scoula.documentAnalysis.dto.IllegalBuildingCheckDTO;

import java.util.List;

public interface DocumentAnalysisService {
    void insertIllegalBuildingData(IllegalBuildingCheckDTO illegalBuildingCheckDTO);

    DocumentAnalysisResultDTO analysis(DocumentAnalysisDTO answerDTOList);

    List<AgentDetailVO> checkDocumentAgentByAgentDTO(DocumentAgentDTO documentAgentDTO);

    List<AgentDetailVO> checkDocumentAgentByAddress(String houseAddress);
}
