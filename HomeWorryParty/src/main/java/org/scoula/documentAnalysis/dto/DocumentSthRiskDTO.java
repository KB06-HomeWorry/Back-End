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
    private String price;
    private List<String> selectedOptions;
    private String type;
}
