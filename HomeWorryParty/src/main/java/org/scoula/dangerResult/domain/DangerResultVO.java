package org.scoula.dangerResult.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DangerResultVO {
    private String grade;
    private long templateId;
    private float minScore;
    private float maxScore;
    private String message;
    private String description;
    private String imageUrl;

    public void copy(DangerResultVO dangerResultVO) {
        this.grade = dangerResultVO.getGrade();
        this.templateId = dangerResultVO.getTemplateId();
        this.minScore = dangerResultVO.getMinScore();
        this.maxScore = dangerResultVO.getMaxScore();
        this.message = new String(dangerResultVO.getMessage());
        this.imageUrl = new String(dangerResultVO.getImageUrl());
    }
}
