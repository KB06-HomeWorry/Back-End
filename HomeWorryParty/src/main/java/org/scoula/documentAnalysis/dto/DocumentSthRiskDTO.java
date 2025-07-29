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
    // 매물의 시세와 평수로 위험도를 분석하기 위한 DTO
    private Long price;
    private Long monthlyPrice;
    private Long size;
    private String type;
}
