package org.scoula.documentAnalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentSthRiskDTO {
    private int optionCount;
    private String deposit;
    private String monthlyFee;
    private List<String> selectedOptions;
    private String type;
}
