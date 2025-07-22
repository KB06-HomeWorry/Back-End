package org.scoula.dangerResult.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DangerResultDTO {
    private String grade;          // 등급(예: Low/Medium/High)
    private long templateId;
    private float minScore;        // 등급별 최소 점수
    private float maxScore;        // 등급별 최대 점수
    private String message;        // 사용자 안내문구
    private List<String> descriptionTitleList;
    private List<String> descriptionContentList;
    private String imageUrl;       // 안내 이미지(아이콘/배너 등) URL
}
