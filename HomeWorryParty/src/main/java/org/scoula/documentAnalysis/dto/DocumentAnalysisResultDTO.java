package org.scoula.documentAnalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentAnalysisResultDTO {
    // 분석한 결과를 front에 넘겨주기 위한 DTO
    private int score = 100;
    private String grade;          // 등급(예: Low/Medium/High)
    private String message;        // 사용자 안내문구

    private List<String> descriptionTitleList = new ArrayList<>();
    private List<String> descriptionContentList = new ArrayList<>();
    private String imageUrl;       // 안내 이미지(아이콘/배너 등) URL

    public void setResultData() {
        if (score < 40) {
            grade = "VeryHigh";
            message = "거래 주의";
            imageUrl = "/src/assets/icons/risk_veryhigh.png";
        } else if (score < 60) {
            grade = "High";
            message = "위험 높음";
            imageUrl = "/src/assets/icons/risk_high.png";
        } else if (score < 80) {
            grade = "Medium";
            message = "주의 필요";
            imageUrl = "/src/assets/icons/risk_medium.png";
        } else {
            grade = "Low";
            message = "위험 낮음";
            imageUrl = "/src/assets/icons/risk_low.png";
        }
    }
}
