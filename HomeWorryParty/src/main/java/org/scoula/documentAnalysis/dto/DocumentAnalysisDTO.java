package org.scoula.documentAnalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentAnalysisDTO {
    // 중개사 서류 분석을 위해 front에게 받는 DTO
    private int registerCertifiedCount;
    private String houseAddress;
    private DocumentAgentDTO documentAgentDTO;
    private DocumentSthRiskDTO documentSthRiskDTO;
}
