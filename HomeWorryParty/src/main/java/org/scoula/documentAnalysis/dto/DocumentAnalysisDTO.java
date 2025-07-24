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
    private int registerCertifiedCount;
    private String houseAddress;
    private DocumentAgentDTO documentAgentDTO;
    private DocumentSthRiskDTO documentSthRiskDTO;
}
